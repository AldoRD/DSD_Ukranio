// Proyecto 3
// Ramirez Dominguez Aldo Eduardo
// 4CM14

import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

/*
    1.- ¿Cuántos votos totales se han realizado por sexo?
    2.- ¿Cuántos votos totales se han realizado por cada estado de la república?
    3.- ¿Cuántos votos se han realizado por ciudadanos de x años de edad?
    4.- ¿Cuántos votos van por cada partido?
 */

public class ConsultorEstadisticas {
    public static ArrayList<String> allVotos = new ArrayList<>();
    public static Map<String, Integer> votosFiltrados = new HashMap<>();

    public static void main(String[] args) {
        String archivo = "curps.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            while (true) {
                getAllVotos(br);
                generarMenu();
                selectorDefiltro();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Esta función genera un menú con diferentes opciones de filtro para los datos
     * de votación.
     */
    public static void generarMenu() {
        System.out.print("\033[H\033[2J");
        System.out.println("Menu De Filtros");
        System.out.println("=====================================================");
        System.out.println("[1] Votos totales por sexo");
        System.out.println("[2] Votos totales por cada estado de la república");
        System.out.println("[3] Votos por ciudadanos de x años de edad");
        System.out.println("[4] Votos van por cada partido");
        System.out.println("=====================================================");
        System.out.println("Seleccione una opción: ");

    }

    /**
     * Esta función lee una entrada de número entero del usuario a través de la
     * consola y
     * lo devuelve, o
     * devuelve -1 si ocurre una excepción.
     */
    public static int getInput() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String input = br.readLine();
            int inputInt = Integer.parseInt(input);
            return inputInt;
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Esta función usa una declaración de cambio para seleccionar y ejecutar
     * diferentes opciones
     * basado en la entrada del usuario.
     */
    public static void selectorDefiltro() {
        try {
            int input = getInput();
            switch (input) {
                case 1:
                    System.out.println("Opcion 1");
                    System.out.println(getVotosBySexo());
                    esperar();
                    break;
                case 2:
                    System.out.println("Opcion 2");
                    System.out.println(getVotosByEstados());
                    esperar();
                    break;
                case 3:
                    System.out.println("Opcion 3");
                    System.out.println(getVotosByEdad());
                    esperar();
                    break;
                case 4:
                    System.out.println("Opcion 4");
                    System.out.println(getVotosByPartido());
                    esperar();
                    break;
                default:
                    System.out.println("Opción no valida");
                    esperar();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Esta función de Java espera a que el usuario presione la tecla enter antes
     * continuación de la ejecución.
     */
    public static void esperar() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\n\t\tPRESIONE ENTER PARA CONTINUAR..."); // Mensaje en pantalla
        try {
            br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Esta función devuelve un mapa del número de votos por género.
     */
    public static Map<String, Integer> getVotosBySexo() {
        Map<String, Integer> filtrados = new HashMap<>();
        allVotos.forEach((v) -> {
            String sexo = v.substring(10, 11);
            filtrados.put(sexo, filtrados.getOrDefault(sexo, 0) + 1);
        });
        return filtrados;
    }

    /**
     * Esta función de Java devuelve un mapa del número de votos por estado.
     */
    public static Map<String, Integer> getVotosByEstados() {
        Map<String, Integer> filtrados = new HashMap<>();
        allVotos.forEach((v) -> {
            String entidad = v.substring(11, 13);
            filtrados.put(entidad, filtrados.getOrDefault(entidad, 0) + 1);
        });
        return filtrados;
    }

    /**
     * Esta función de Java devuelve un mapa del número de votos agrupados por edad.
     */
    public static Map<String, Integer> getVotosByEdad() {
        Map<String, Integer> filtrados = new HashMap<>();
        allVotos.forEach((v) -> {
            Date fechaNacimiento = obtenerFecha(v.substring(4, 10));
            int res = calcularEdad(fechaNacimiento);
            filtrados.put(String.valueOf(res), filtrados.getOrDefault(String.valueOf(res), 0) + 1);
        });
        return filtrados;
    }

    /**
     * Esta función devuelve un mapa del número de votos de cada partido político.
     */
    public static Map<String, Integer> getVotosByPartido() {
        Map<String, Integer> filtrados = new HashMap<>();
        allVotos.forEach((v) -> {
            String partido = v.substring(v.length() - 1);
            filtrados.put(partido, filtrados.getOrDefault(partido, 0) + 1);
        });
        return filtrados;
    }

    public static Date obtenerFecha(String fechaNacimientoString) {
        SimpleDateFormat formato = new SimpleDateFormat("yyMMdd");
        try {
            return formato.parse(fechaNacimientoString);
        } catch (java.text.ParseException e) {
            throw new IllegalArgumentException("Fecha de nacimiento no valida");
        }
    }

    public static int calcularEdad(Date fechaNacimiento) {
        Calendar ahora = Calendar.getInstance();
        Calendar fechaNac = Calendar.getInstance();
        fechaNac.setTime(fechaNacimiento);
        int yearNac = fechaNac.get(Calendar.YEAR);
        yearNac = ahora.get(Calendar.YEAR) <= yearNac ? yearNac - 100 : yearNac;
        int edad = ahora.get(Calendar.YEAR) - yearNac;
        if (ahora.get(Calendar.MONTH) < fechaNac.get(Calendar.MONTH)) {
            edad--;
        } else if (ahora.get(Calendar.MONTH) == fechaNac.get(Calendar.MONTH)
                && ahora.get(Calendar.DAY_OF_MONTH) < fechaNac.get(Calendar.DAY_OF_MONTH)) {
            edad--;
        }
        return edad;
    }

    /**
     * La función lee líneas de un BufferedReader y las agrega a una lista llamada
     * todos los votos.
     */
    public static void getAllVotos(BufferedReader br) {
        try {
            String linea;
            while ((linea = br.readLine()) != null)
                allVotos.add(linea);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
