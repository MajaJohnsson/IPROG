/**
 * Klass för att skapa en tråd
 */
public class T1 extends Thread {
    private volatile boolean alive = true;

    /**
     * Startar tråden
     */
    public void t1Start() {
        start();
    }

    /**    (non-Javadoc)
     * Instruktioner som körs när tråden startas
     * @see java.lang.Thread#run()
     */
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

    /**
     * Stoppar tråden
     */
    public void end() {
        alive = false;
        //running = false;
    }
}
