package simplethreads;

public class SimpleThread {

  public static void main(final String[] args) throws java.io.IOException {

    // create a sample thread

    final Thread x = new Thread(new Runnable() {
      @Override public void run() {
        while (! Thread.interrupted()) {
          for (long i = 0; i < 1000000; i ++) {
            new Object();
          }
          System.out.println("hello");
        }
        System.out.println("zork");
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          System.out.println("interrupted 1");
          return;
        }
        System.out.println("blah");
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          System.out.println("interrupted 2");
          return;
        }
        System.out.println("kuck");
      }
    });

    // start the thread up
    x.start();
    System.out.println("started thread, waiting...");
    try {
      Thread.sleep(1500);
      System.out.println("continuing...");
      x.interrupt();
      // wait for the thread to finish (this happens once its
      // run method terminates)
      System.out.println(x.isInterrupted());
      System.out.println(x.isAlive());
      // x.interrupt();
      x.join();
    } catch (InterruptedException e) {
      System.out.println("thread was interrupted");
    }
    System.out.println("done");
  }
}
