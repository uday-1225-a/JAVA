package model;

import exception.AccountNotFoundException;
import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;

public class Account {
    private final String accountNumber;
    private String ownerName;
    private long balance;

    public Account(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public long getBalance() { return balance; }

    // Step 2: deposit declaring throws InvalidAmountException
    public void deposit(long amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive. Received: ₹" + amount);
        }
        balance += amount;
        System.out.println("Successfully deposited ₹" + amount + " to " + accountNumber + ". New Balance: ₹" + balance);
    }

    // Step 2: withdraw declaring throws InsufficientFundsException, InvalidAmountException
    public void withdraw(long amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero. Received: ₹" + amount);
        }
        if (balance < amount) {
            long shortfall = amount - balance;
            throw new InsufficientFundsException("Insufficient funds! Cannot withdraw ₹" + amount + ". Short by ₹" + shortfall, shortfall);
        }
        balance -= amount;
        System.out.println("Successfully withdrawn ₹" + amount + " from " + accountNumber + ". Remaining Balance: ₹" + balance);
    }

    // Step 3: transfer method using try, catch, finally and re-throwing BankException
    public void transfer(Account to, long amount) throws BankException {
        if (to == null) {
            throw new AccountNotFoundException("Destination account not found (null reference).");
        }
        System.out.println("\n[Transfer Initiated]: ₹" + amount + " from " + this.accountNumber + " to " + to.accountNumber);
        try {
            this.withdraw(amount);
            to.deposit(amount);
            System.out.println("[Transfer Completed]: Funds credited successfully to " + to.accountNumber);
        } catch (BankException e) {
            System.out.println("[Transfer Error]: " + e.getMessage());
            throw e; // re-throw exception
        } finally {
            System.out.println("[Transfer Audit Log]: Completed processing transfer between " + this.accountNumber + " and " + to.accountNumber);
        }
    }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | ₹" + balance;
    }
}

