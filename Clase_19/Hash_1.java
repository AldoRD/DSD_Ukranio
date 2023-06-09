import java.io.*;
import java.util.*;

public class Hash_1 {

    public static void main(String[] args) throws Exception {
        File file = new File("El_viejo_y_el_mar.txt");
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        Map<String, Integer> hm = new HashMap<String, Integer>();

        int c;
        String str;

        while ((c = buffer.read()) != -1) {
            str = String.valueOf((char) c);
            hm.put(str, hm.getOrDefault(str, 0) + 1);
        }

        for (Map.Entry<String, Integer> e : hm.entrySet()) {
            System.out.println(" - " + e.getKey() + " " + e.getValue());
        }

    }
}
