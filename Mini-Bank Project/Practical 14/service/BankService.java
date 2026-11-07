package service;

import annotation.Audit;
import exception.AccountNotFoundException;
import exception.BankException;
import exception.InvalidAmountException;
import model.Account;
import model.SavingsAccount;
import repository.Repository;
import util.AuditLogger;

import java.util.List;

public class BankService {
    private final Repository<Account> repository;

    public BankService(Repository<Account> repository) {
        this.repository = repository;
    }

    @Audit(action = "OPEN_ACCOUNT")
    public Account openAccount(String accountNumber, String ownerName, long initialBalance, long minBalance) throws InvalidAmountException {
        AuditLogger.log(BankService.class, "openAccount");
        if (initialBalance < minBalance) {
            throw new InvalidAmountException("Initial balance (₹" + initialBalance + ") must meet minimum balance requirement of ₹" + minBalance);
        }
        Account acc = new SavingsAccount(accountNumber, ownerName, initialBalance, minBalance);
        repository.save(acc);
        return acc;
    }

    @Audit(action = "DEPOSIT")
    public void deposit(String accountNumber, long amount) throws BankException {
        AuditLogger.log(BankService.class, "deposit");
        Account acc = repository.findById(accountNumber);
        if (acc == null) throw new AccountNotFoundException("Account " + accountNumber + " not found!");
        acc.deposit(amount);
    }

    @Audit(action = "WITHDRAW")
    public void withdraw(String accountNumber, long amount) throws BankException {
        AuditLogger.log(BankService.class, "withdraw");
        Account acc = repository.findById(accountNumber);
        if (acc == null) throw new AccountNotFoundException("Account " + accountNumber + " not found!");
        acc.withdraw(amount);
    }

    @Audit(action = "TRANSFER")
    public void transfer(String fromAccNo, String toAccNo, long amount) throws BankException {
        AuditLogger.log(BankService.class, "transfer");
        if (fromAccNo.equals(toAccNo)) {
            throw new BankException("Source and destination accounts cannot be the same.");
        }
        Account from = repository.findById(fromAccNo);
        Account to = repository.findById(toAccNo);

        if (from == null) throw new AccountNotFoundException("Source account " + fromAccNo + " not found!");
        if (to == null) throw new AccountNotFoundException("Destination account " + toAccNo + " not found!");

        // Deadlock prevention: smaller account locked first
        Account first = from.getAccountNumber().compareTo(to.getAccountNumber()) < 0 ? from : to;
        Account second = from.getAccountNumber().compareTo(to.getAccountNumber()) < 0 ? to : from;

        synchronized (first) {
            synchronized (second) {
                from.withdraw(amount);
                to.deposit(amount);
            }
        }
    }

    @Audit(action = "STATEMENT")
    public String statement(String accountNumber) throws AccountNotFoundException {
        AuditLogger.log(BankService.class, "statement");
        Account acc = repository.findById(accountNumber);
        if (acc == null) throw new AccountNotFoundException("Account " + accountNumber + " not found!");

        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("        MINIBANK OFFICIAL STATEMENT      \n");
        sb.append("========================================\n");
        sb.append("Account Number : ").append(acc.getAccountNumber()).append("\n");
        sb.append("Account Holder : ").append(acc.getOwnerName()).append("\n");
        sb.append("Account Status : ").append(acc.isActive() ? "ACTIVE" : "INACTIVE").append("\n");
        sb.append("Current Balance: ₹").append(acc.getBalance()).append("\n");
        sb.append("========================================\n");
        return sb.toString();
    }

    public List<Account> listAccounts() {
        var list = repository.findAll();
        list.sort(null); // natural sort by account number
        return list;
    }
}

