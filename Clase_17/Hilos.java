public class Hilos implements Runnable {
    static int n;
    static int variable_compartida = 0;
    static Thread t1;
    static Thread t2;

    public static void main(String[] args) throws InterruptedException {
        Hilos t = new Hilos();
        t1 = new Thread(t, "1");
        t2 = new Thread(t, "2");
        n = Integer.parseInt(args[0]);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("El valor de la variable compartida es: " + variable_compartida);

    }

    public synchronized void modifica() {
        if (Thread.currentThread().getName().equalsIgnoreCase("1")) {
            variable_compartida++;
        } else if (Thread.currentThread().getName().equalsIgnoreCase("2")) {
            variable_compartida--;
        }
    }

    public void run() {
        for (int i = 0; i < n; i++) {
            modifica();
        }
    }
}