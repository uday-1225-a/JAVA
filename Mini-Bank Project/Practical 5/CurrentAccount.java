public class CurrentAccount extends Account {
    private long overdraftLimit;

    public CurrentAccount(String accountNumber, String ownerName, long balance, long overdraftLimit) {
        super(accountNumber, ownerName, balance);
        this.overdraftLimit = overdraftLimit;
    }

    public long getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public double interestRate() {
        return 0.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return (balance - amount) >= -overdraftLimit;
    }
}

