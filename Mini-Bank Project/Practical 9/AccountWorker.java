public class AccountWorker implements Runnable {
    private final Account account;
    private final int times;
    private final long amount;
    private final boolean useSync;

    public AccountWorker(Account account, int times, long amount, boolean useSync) {
        this.account = account;
        this.times = times;
        this.amount = amount;
        this.useSync = useSync;
    }

    @Override
    public void run() {
        System.out.println("Thread [" + Thread.currentThread().getName() + "] STARTED running.");
        for (int i = 0; i < times; i++) {
            if (useSync) {
                account.deposit(amount);
            } else {
                account.depositUnsafe(amount);
            }
        }
        System.out.println("Thread [" + Thread.currentThread().getName() + "] COMPLETED " + times + " deposits.");
    }
}

