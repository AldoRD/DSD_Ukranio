// Proyecto 2
// Ramirez Dominguez Aldo Eduardo
// 4CM14

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class SimpleGui extends JFrame {
    private static Panel p;
    private static int numPoligonos;
    private static ArrayList<PoligonoReg> poligonos = new ArrayList<PoligonoReg>();

    public static void main(String[] args) {
        numPoligonos = Integer.parseInt(args[0]);
        crearPoligonos();

        SimpleGui gui = new SimpleGui();
        gui.setVisible(true);

        p.dibujarPoligonos();
        Collections.sort(poligonos, new SortByArea());
        p.dibujarPoligonos();
    }

    public SimpleGui() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        p = new Panel();
        add(p);
    }

    public static void crearPoligonos() {
        for (int i = 0; i < numPoligonos; i++) {
            int numLados = (int) getRandomNumberRange(3, 10);
            poligonos.add(new PoligonoReg(numLados));
        }
    }

    public static double getRandomNumberRange(int min, int max) {
        Random r = new Random();
        double random = min + r.nextDouble() * (max - min);
        return random;
    }

    private class Panel extends JPanel {
        private int count = 0;
        private boolean isSortedPoligonos = false;
        private Polygon currentPolygon = null;

        @Override
        public void paintComponent(Graphics g) {
            if (currentPolygon != null) {
                g.setColor(Color.blue);
                g.drawPolygon(currentPolygon);
            }
        }

        public void dibujarPoligonos() {
            Dimension dimension = getSize();
            while (count < poligonos.size()) {
                double x = isSortedPoligonos ? dimension.getWidth() / 2 : getRandomNumberRange(100, 500);
                double y = isSortedPoligonos ? dimension.getHeight() / 2 : getRandomNumberRange(100, 500);
                currentPolygon = poligonos.get(count).getPolygon(x, y);
                repaint();
                delayInMilis(500);
                count = count + 1;
            }
            limpiar();
        }

        public void limpiar() {
            delayInMilis(3000);
            count = 0;
            currentPolygon = null;
            isSortedPoligonos = true;
            Dimension dimension = getSize();
            int ancho = (int) dimension.getWidth();
            int alto = (int) dimension.getHeight();
            Graphics g = getGraphics();
            g.setColor(getBackground());
            g.fillRect(0, 0, ancho, alto);
        }

        public void delayInMilis(int milis) {
            try {
                Thread.sleep(milis);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

}

class SortByArea implements Comparator<PoligonoReg> {
    @Override
    public int compare(PoligonoReg c1, PoligonoReg c2) {
        return (int) (c1.obtieneArea() - c2.obtieneArea());
    }
}