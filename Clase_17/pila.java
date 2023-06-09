
public class pila implements Runnable {
    // char[] pilac = new char[10];
    private char buffer[];
    private int siguiente = 0;
    private boolean estaLlena = false;
    public boolean estaVacia = true;
    // private final String letras = "abcdefghijklmnopqrstuvxyz";

    public void consumir() {
        while (estaVacia == true) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        siguiente--;
        if (siguiente == 0) {
            estaVacia = true;
        }
        estaLlena = false;
        // notifyAll();
        // return (buffer[siguiente]);
    }

    public void producir() {
        while (estaLlena == true) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        buffer[siguiente] = 'x';
        siguiente++;
        estaVacia = false;
        if (siguiente == buffer.length) {
            estaLlena = true;
        }
        estaVacia = false;
        // notifyAll();
    }

    public void imprimirPantalla() {
        int x = 0;
        if (estaVacia == false) {
            for (int i = 0; i < buffer.length; i++) {
                if (buffer[i] == 'x') {
                    x++;
                }
                System.out.print(buffer[i]);
            }
            System.out.println("\nTope: " + x);
        } else {
            System.out.println("\nTope: 0");
        }
    }

    public pila() {
        buffer = new char[10];
        System.out.println("\nTope: " + (siguiente));
    }

    public void run() {
        while (true) {
            modifica();
            try {
                if (Thread.currentThread().getName().equals("p")) {
                    Thread.sleep((int) (Math.random() * 200));
                    return;

                } else if (Thread.currentThread().getName().equals("c")) {
                    Thread.sleep((int) (Math.random() * 4000));
                    return;
                }
                Thread.sleep((int) (Math.random() * 300));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void modifica() {
        if (Thread.currentThread().getName().equals("i")) {
            clearConsole();
            imprimirPantalla();
            // System.out.println("Depositado el caracter x" );
        } else if (Thread.currentThread().getName().equals("p")) {
            // char valor = consumir();
            consumir();
            // System.out.println("valor consumido " + valor);
        } else if (Thread.currentThread().getName().equals("c")) {
            producir();
        }
    }

    public final static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            }
            // System.out.println(os);
        } catch (final Exception e) {
            // Handle any exceptions.
        }
    }

    public static void main(String[] args) throws Exception {
        try {

            pila t = new pila();

            Thread p = new Thread(t, "p");
            Thread c = new Thread(t, "c");
            Thread i = new Thread(t, "i");

            i.start();
            p.start();
            c.start();

            p.join(0);
            c.join(0);
            i.join(0);

        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
