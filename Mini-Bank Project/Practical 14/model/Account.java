package model;

import annotation.Id;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import java.io.Serializable;

public class Account implements Serializable, Comparable<Account> {
    private static final long serialVersionUID = 1L;

    @Id
    protected final String accountNumber;
    protected String ownerName;
    protected long balance;
    protected boolean active;

    public Account(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
        this.active = true;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public synchronized long getBalance() { return balance; }
    public boolean isActive() { return active; }

    public synchronized void deposit(long amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive. Received: ₹" + amount);
        }
        balance += amount;
    }

    public synchronized void withdraw(long amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero. Received: ₹" + amount);
        }
        if (balance < amount) {
            long shortfall = amount - balance;
            throw new InsufficientFundsException("Insufficient balance for withdrawal! Short by ₹" + shortfall, shortfall);
        }
        balance -= amount;
    }

    @Override
    public int compareTo(Account other) {
        return this.accountNumber.compareTo(other.accountNumber);
    }

    @Override
    public String toString() {
        return String.format("%-8s | %-16s | ₹%-8d | %s", accountNumber, ownerName, balance, (active ? "ACTIVE" : "INACTIVE"));
    }
}

