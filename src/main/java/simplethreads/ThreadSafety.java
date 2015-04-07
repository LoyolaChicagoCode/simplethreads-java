package simplethreads;

public class ThreadSafety {

    static final int ITERATIONS = 10000;

    static final int THREAD_COUNT = 4;

    int shared = 0;

    final Object lock = new Object();

    final Runnable incrementUnsafe = new Runnable() {
        @Override
        public void run() {
            final int local = shared;
            tinyDelay();
            shared = local + 1;
        }
    };

    final Runnable incrementSafe = new Runnable() {
        @Override
        public void run() {
            synchronized (lock) {
                final int local = shared;
                tinyDelay();
                shared = local + 1;
            }
        }
    };

    public static void tinyDelay() {
        try {
            Thread.sleep(0);
        } catch (final InterruptedException e) {
            throw new RuntimeException("interrupted during sleep");
        }
    }

    public int runConcurrently(final Runnable inc, final int threadCount) {
        shared = 0;
        final Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i += 1) {
            threads[i] = new Thread(inc);
        }
        for (final Thread t : threads) {
            t.start();
        }
        for (final Thread t : threads) {
            try {
                t.join();
            } catch (final InterruptedException e) {
                throw new RuntimeException("interrupted during join");
            }
        }
        return shared;
    }

    public int countErrors(final Runnable inc, int threadCount, int iterations) {
        int count = 0;
        for (int i = 0; i < iterations; i += 1) {
            int result = runConcurrently(inc, threadCount);
            if (result != threadCount) {
                count += 1;
            }
        }
        return count;
    }

    public static void main(final String[] args) {
        System.out.println("Performing " + ITERATIONS + " iterations with " + THREAD_COUNT + " threads per iteration");
        final ThreadSafety ts = new ThreadSafety();
        final int errorsUnsafe = ts.countErrors(ts.incrementUnsafe, 4, ITERATIONS);
        final int errorsSafe = ts.countErrors(ts.incrementSafe, 4, ITERATIONS);
        System.out.println("Error rate without locking: " + (errorsUnsafe * 100 / ITERATIONS) + "%");
        System.out.println("Error rate with locking:    " + (errorsSafe * 100 / ITERATIONS) + "%");
    }
}
