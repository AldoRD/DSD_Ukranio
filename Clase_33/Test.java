import java.io.*;
import java.util.*;

class Demo implements java.io.Serializable

{

    public String a;
    public String b;

    // Default constructor
    public Demo(String a, String b) {
        this.a = a;
        this.b = b;
    }
}

class Test {
    public static void main(String[] args) {
        Demo object = new Demo("2018", "6306");
        Demo objeto = null;

        List<byte[]> arreglo = new ArrayList<>();

        byte[] serializado = SerializationUtils.serialize(object);
        System.out.println(serializado);

        arreglo.add(serializado);

        objeto = (Demo) SerializationUtils.deserialize(serializado);
        System.out.println("a = " + objeto.a);
        System.out.println("b = " + objeto.b);

        System.out.println("Arraylist\n");

        Demo auxiliar = (Demo) SerializationUtils.deserialize(arreglo.get(0));

        System.out.println("Auxiliar b = " + auxiliar.b);
        System.out.println("Auxiliar a = " + auxiliar.a);

    }
}