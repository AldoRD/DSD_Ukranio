import java.io.*;
import java.util.*;

public class Hash_2 {

    public static void main(String[] args) throws Exception {
        File file = new File("BIBLIA_COMPLETA.txt");
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        Map<String, Integer> hm = new HashMap<String, Integer>();
        Map<String, Integer> ordered = new LinkedHashMap<String, Integer>();
        ArrayList<Integer> list = new ArrayList<Integer>();

        int c;
        String str;

        while ((c = buffer.read()) != -1) {
            str = String.valueOf((char) c);
            hm.put(str, hm.getOrDefault(str, 0) + 1);
        }

        for (Map.Entry<String, Integer> e : hm.entrySet()) {
            list.add(e.getValue());
        }

        Collections.sort(list, new SortByCount());

        for (Integer aux : list) {
            for (Map.Entry<String, Integer> e : hm.entrySet()) {
                if (e.getValue() == aux) {
                    ordered.put(e.getKey(), aux);
                }
            }
        }

        System.out.println(ordered);

        // for (Map.Entry<String, Integer> e : ordered.entrySet()) {
        // System.out.println(" - " + e.getKey() + " " + e.getValue());
        // }
    }
}

class SortByCount implements Comparator<Integer> {
    @Override
    public int compare(Integer c1, Integer c2) {
        return c2.compareTo(c1);
    }
}
