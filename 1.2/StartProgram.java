public class StartProgram {
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