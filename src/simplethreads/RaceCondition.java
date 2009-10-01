package simplethreads;

/**
 * This class illustrates the race condition arising from concurrent
 * access to shared data.  If the waiting time between the read and the
 * write access (see AccessShared.run) is greater than zero, most
 * executions lead to the wrong result.  Access can be made sequential
 * by using an object as a lock (this code is currently commented out).
 */

public class RaceCondition {

  static Object lock = new Object();

  static Shared shared = new Shared();

  public static void main(String[] args) throws InterruptedException {
    while (true) {
      Thread t1 = new Thread(new AccessShared());
      Thread t2 = new Thread(new AccessShared());
      shared.reset();
      t1.start();
      t2.start();
      t1.join();
      t2.join();
      System.out.println("shared = " + shared.get());
    }
  }

  static class AccessShared implements Runnable {

    public void run() {
//      synchronized (lock) {
        shared.inc();
//      }
    }
  }
  static class AccessShared2 implements Runnable {

    public void run() {
//      synchronized (lock) {
        shared.inc2();
//      }
    }
  }
}

class Shared {

  private int value = 0;

  public /*synchronized*/ void inc() {
    try {
      int local = value;
      if (false)
        Thread.sleep(0);
      Thread.yield();
      value = local + 1;
    }
    catch (InterruptedException e) {
      System.err.println(this +" interrupted while sleeping");
    }
  }

  public /*synchronized*/ void inc2() {
    try {
      int local = value;
      if (false)
        Thread.sleep(0);
      Thread.yield();
      value = local + 1;
    }
    catch (InterruptedException e) {
      System.err.println(this +" interrupted while sleeping");
    }
  }

  public /*synchronized*/ int get() {
    return value;
  }

  public /*synchronized*/ void reset() {
    value = 0;
  }
}
