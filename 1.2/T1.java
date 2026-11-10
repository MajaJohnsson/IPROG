public class T1 extends Thread {
    private volatile boolean alive = true;

    public void t1Start() {
        start();
    }

    public void run() {
        while (alive) {
            //while (running) {
                System.out.println("Tråd 1");
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
