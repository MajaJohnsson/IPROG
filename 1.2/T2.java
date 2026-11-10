public class T2 implements Runnable {
    private volatile boolean alive = true;
    Thread t = new Thread(this);

    public void t2Start() {
        t.start();
    }

    public void run() {
        while (alive) {
            //while (running) {
                System.out.println("Tråd 2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            //}
        }
    }

    public void end() {
        alive = false;
        //running = false;
    }
}