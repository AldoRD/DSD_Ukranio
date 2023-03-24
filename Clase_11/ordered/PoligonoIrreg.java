import java.util.ArrayList;
import java.util.Comparator;
import java.text.DecimalFormat;
import java.util.*;

public class PoligonoIrreg {
	ArrayList<Coordenada> vertices;

	public PoligonoIrreg() {
		vertices = new ArrayList<Coordenada>();
	}

	public void anadeVertice(Coordenada c1) {
		vertices.add(c1);
	}

	public void ordenaVertice() {
		Collections.sort(vertices, new SortByMagnitude());
	}

	@Override
	public String toString() {
		String str = "| X | Y |\n";
		DecimalFormat formato = new DecimalFormat("#.00");
		for (Coordenada coor : vertices)
			str = str + "[" + formato.format(coor.abcisa()) + "," + formato.format(coor.ordenada()) + "] Magnitud:"
					+ coor.getMagnitud() + "\n";

		return str;
	}
}

class SortByMagnitude implements Comparator<Coordenada> {
	@Override
	public int compare(Coordenada c1, Coordenada c2) {
		return (int) (c1.getMagnitud() - c2.getMagnitud());
	}
}
