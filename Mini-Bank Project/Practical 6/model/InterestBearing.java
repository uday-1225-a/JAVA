package model;

public interface InterestBearing {
    double interestRate();
    long getBalance();

    // Default method calculating yearly interest
    default double yearlyInterest() {
        return (getBalance() * interestRate()) / 100.0;
    }
}

