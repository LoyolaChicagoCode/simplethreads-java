package simplethreads;

public class MultipleThreads {
  public static void main(final String[] args) throws Exception {
    final int sleepTime = Integer.parseInt(args[0]);
    if (sleepTime < 0) {
      // run sequentially
      new Printer("baz", 15, 1).run();
      new Printer("bif", 15, 1).run();
      new Printer("bam", 15, 1).run();
    } else {
      // create and start several threads
      new Thread(new Printer("baz", 15, sleepTime)).start();
      new Thread(new Printer("bif", 15, sleepTime)).start();
      new Thread(new Printer("bam", 15, sleepTime)).start();
    }
  }
}

/**
 * The class Printer is a Runnable whose run method prints a message a
 * specified number of times with a specified waiting time between
 * messages.
 */

class Printer implements Runnable {

  final String message;
  final int numberOfTimes;
  final int waitingTime;

  public Printer(final String message, final int numberOfTimes, final int waitingTime) {
    this.message = message;
    this.numberOfTimes = numberOfTimes;
    this.waitingTime = waitingTime;
  }

  @Override
  public void run() {
    for (int i = 0; i < numberOfTimes; i ++) {
      try {
        if (waitingTime > 0) {
          Thread.sleep(waitingTime);
        }
      } catch (InterruptedException e) {
        System.err.println("interrupted while sleeping");
        return;
      }
      System.out.println(message);
    }
  }
}
