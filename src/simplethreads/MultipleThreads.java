package simplethreads;

public class MultipleThreads {
    public static void main(String[] args) throws java.io.IOException {
//      new Printer("baz", 15, 1).run();
//      new Printer("bif", 15, 1).run();
//      new Printer("bam", 15, 1).run();
      // create and start several threads
      new Thread(new Printer("baz", 15, 0)).start();
      new Thread(new Printer("bif", 15, 0)).start();
      new Thread(new Printer("bam", 15, 0)).start();
    }
}

/**
 * The class Printer is a Runnable whose run method prints a message a
 * specified number of times with a specified waiting time between
 * messages.
 */

class Printer implements Runnable {

  String message;
  int numberOfTimes;
  int waitingTime;

  public Printer(String message, int numberOfTimes, int waitingTime) {
    this.message = message;
    this.numberOfTimes = numberOfTimes;
    this.waitingTime = waitingTime;
  }

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
