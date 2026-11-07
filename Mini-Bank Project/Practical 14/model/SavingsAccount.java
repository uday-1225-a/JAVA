package model;

import exception.InsufficientFundsException;
import exception.InvalidAmountException;

public class SavingsAccount extends Account {
    private static final long serialVersionUID = 1L;
    private final long minBalance;

    public SavingsAccount(String accountNumber, String ownerName, long balance, long minBalance) {
        super(accountNumber, ownerName, balance);
        this.minBalance = minBalance;
    }

    public long getMinBalance() { return minBalance; }

    @Override
    public synchronized void withdraw(long amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero.");
        }
        if (balance - amount < minBalance) {
            long shortfall = (minBalance + amount) - balance;
            throw new InsufficientFundsException("Minimum balance violation! Must maintain ₹" + minBalance + ". Short by ₹" + shortfall, shortfall);
        }
        balance -= amount;
    }
}

