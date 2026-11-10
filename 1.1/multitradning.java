//private volatile boolean running = true;
/*
class StartProgram {
    public static void main(String[] args) {
    
        try {
            T1 t1 = new T1();
            t1.t1Start(); 
            Thread.sleep(5000);

            T2 t2 = new T2();
            t2.t2Start();
            Thread.sleep(5000);

            t1.end();

            Thread.sleep(5000);

            t2.end();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class T1 extends Thread {
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

class T2 implements Runnable {
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
    */