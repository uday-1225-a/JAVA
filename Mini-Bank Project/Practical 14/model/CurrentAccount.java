package model;

import exception.InsufficientFundsException;
import exception.InvalidAmountException;

public class CurrentAccount extends Account {
    private static final long serialVersionUID = 1L;
    private final long overdraftLimit;

    public CurrentAccount(String accountNumber, String ownerName, long balance, long overdraftLimit) {
        super(accountNumber, ownerName, balance);
        this.overdraftLimit = overdraftLimit;
    }

    public long getOverdraftLimit() { return overdraftLimit; }

    @Override
    public synchronized void withdraw(long amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero.");
        }
        if (balance - amount < -overdraftLimit) {
            long shortfall = amount - (balance + overdraftLimit);
            throw new InsufficientFundsException("Overdraft limit exceeded! Limit: ₹" + overdraftLimit + ". Short by ₹" + shortfall, shortfall);
        }
        balance -= amount;
    }
}

