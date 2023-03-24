import java.util.ArrayList;
import java.util.Comparator;

public class PoligonoIrreg {
	ArrayList<Coordenada> vertices;
	
	public PoligonoIrreg (){
		vertices = new ArrayList<Coordenada>();
	}

    public void anadeVertice(Coordenada c1){
		vertices.add(c1);
    }

	@Override
	public String toString(){
		String str="| X | Y |\n";
		for( Coordenada coor: vertices)
			str = str +"["+ String.valueOf(coor.abcisa())+","+String.valueOf(coor.ordenada())+"]\n";
		
		return str;
	}
}
