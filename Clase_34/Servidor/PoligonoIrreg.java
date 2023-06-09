import java.util.*;

public class PoligonoIrreg implements java.io.Serializable {
    private List<Coordenada> vertices;

    PoligonoIrreg() {
        vertices = new ArrayList<>();
    }

    @Override
    public String toString() {
        String aux = "| X | Y |\n";

        for (int i = 0; i < vertices.size(); i++) {
            aux = aux + "[" + String.valueOf(vertices.get(i).abcisa()) + ","
                    + String.valueOf(vertices.get(i).ordenada()) + "]\n";
        }
        return aux;
    }

    public void anadeVertice(Coordenada aux) {
        vertices.add(aux);
    }
}