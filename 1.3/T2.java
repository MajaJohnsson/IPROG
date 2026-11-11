/**
 * Klass för att skapa en tråd
 */
public class T2 implements Runnable {
    private volatile boolean alive = true;
    Thread t = new Thread(this);

    /**
     * Startar tråden
     */
    public void t2Start() {
        t.start();
    }

    /**    (non-Javadoc)
     * Instruktioner som körs när tråden startas
     * @see java.lang.Runnable#run()
     */
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

    /**
     * Stoppar tråden
     */
    public void end() {
        alive = false;
        //running = false;
    }
}