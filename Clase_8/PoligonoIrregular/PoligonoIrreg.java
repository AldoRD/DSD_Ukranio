public class PoligonoIrreg {
	private Coordenada[] vertices;
	private int countVertices;
	
	public PoligonoIrreg (int numVertices){
		vertices = new Coordenada[numVertices];
		countVertices = 0;
	}

    public void anadeVertice(Coordenada c1){
        vertices[countVertices] = c1;
        countVertices += 1;
    }

	@Override
	public String toString(){
		String str="| X | Y |\n";
		for(int i=0; i<countVertices ;i++)
			str = str +"["+ String.valueOf(vertices[i].abcisa())+","+String.valueOf(vertices[i].ordenada())+"]\n";
		return str;
	}
}
