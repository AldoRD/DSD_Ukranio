// 23 - Escribe un programa que lea un archivo de texto y que escriba en otro archivo solo las líneas 
// impares del archivo original

import java.io.*;

public class Ejercicio23 {
  public static void main(String[] args) {
    BufferedReader lector = null;
    BufferedWriter escritor = null;

    try {
      lector = new BufferedReader(new FileReader("original.txt"));
      escritor = new BufferedWriter(new FileWriter("destino.txt"));

      String linea;
      int contador = 1;

      while ((linea = lector.readLine()) != null) {
        if (contador % 2 == 1) {
          escritor.write(linea);
          escritor.newLine();
        }
        contador += 1;
      }

      System.out.println("Se realizo la copia de las lineas");
    } catch (IOException e) {
      System.out.println("Error al leer el archivo");
      e.printStackTrace();
    } finally {
      try {
        if (lector != null)
          lector.close();
        if (escritor != null)
          escritor.close();
      } catch (IOException e) {
        System.out.println("Error con los archivos");
        e.printStackTrace();
      }
    }
  }
}

