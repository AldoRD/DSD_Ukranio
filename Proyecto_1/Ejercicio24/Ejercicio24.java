// 24 - Escribe un programa que lea un archivo de texto plano, lo analice y obtenga la cantidad de veces 
// que se encuentra cada letra en el archivo

import java.io.*;
import java.util.*;

public class Ejercicio24{
  public static void main(String[] args) {
    BufferedReader lector = null;
    HashMap<Character, Integer> mapa = new HashMap<>();

    try {
      lector = new BufferedReader(new FileReader("archivo.txt"));

      String line;
      while ((line = lector.readLine()) != null) {
        for (char c : line.toCharArray()) {
          if (Character.isLetter(c)) {
            char letra = Character.toLowerCase(c);
            int cantidad = mapa.getOrDefault(letra, 0);
            mapa.put(letra, cantidad + 1);
          }
        }
      }

      for (char letra = 'a'; letra <= 'z'; letra++) {
        if (mapa.containsKey(letra)) {
          System.out.println("La letra " + letra + " aparece en el archivo" + mapa.get(letra) + " veces.");
        }
      }
    } catch (IOException e) {
      System.out.println("Fallo al leer los archivos");
      e.printStackTrace();
    } finally {
      try {
        if (lector != null) {
          lector.close();
        }
      } catch (IOException e) {
        System.out.println("Fallo con los archivos");
        e.printStackTrace();
      }
    }
  }
}

