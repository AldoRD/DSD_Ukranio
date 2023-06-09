import java.util.*;
import java.io.*;
import java.util.concurrent.*;

// Task class to be executed (Step 1)
class Task implements Runnable {
	private String taskName;
	private String texto;

	public Task(String taskName, String texto) {
		this.taskName = taskName;
		this.texto = texto;
	}

	// Prints task name and sleeps for 1s
	// This Whole process is repeated 5 times
	public synchronized void run() {
		Map<String, Integer> hm = new HashMap<String, Integer>();
		Map<String, Integer> ordered = new LinkedHashMap<String, Integer>();
		ArrayList<Integer> list = new ArrayList<Integer>();
		String str;

		for (char c : texto.toCharArray()) {
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

		System.out.println("\n" + taskName + " complete" + "\n" + ordered);
	}
}

public class Test {
	static final int MAX_T = 1; // Maximo numero de hilos en thread pool
	static StringBuilder inputStr = new StringBuilder();
	static int strLength, subStringLength;// Tamaño de cadenota | 5ta parte de cadenota

	public static void main(String[] args) {

		int threads = Integer.parseInt(args[0]);
		getStringFromFile();

		// creates five tasks
		Runnable r1 = new Task("task 1", inputStr.substring(0, subStringLength));
		Runnable r2 = new Task("task 2", inputStr.substring(subStringLength, subStringLength * 2));
		Runnable r3 = new Task("task 3", inputStr.substring(subStringLength * 2, subStringLength * 3));
		Runnable r4 = new Task("task 4", inputStr.substring(subStringLength * 3, subStringLength * 4));
		Runnable r5 = new Task("task 5", inputStr.substring(subStringLength * 4));

		// creates a thread pool with MAX_T no. of
		// threads as the fixed pool size(Step 2)
		ExecutorService pool = Executors.newFixedThreadPool(threads);

		// passes the Task objects to the pool to execute (Step 3)
		pool.execute(r1);
		pool.execute(r2);
		pool.execute(r3);
		pool.execute(r4);
		pool.execute(r5);

		// pool shutdown ( Step 4)
		pool.shutdown();
	}

	public static void getStringFromFile() {
		try {

			File archivo = new File("BIBLIA_COMPLETA.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(archivo), "utf-8"));
			String linea;

			while ((linea = br.readLine()) != null)
				inputStr.append(linea);

			strLength = inputStr.length();
			subStringLength = strLength / 5;

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class SortByCount implements Comparator<Integer> {
	@Override
	public int compare(Integer c1, Integer c2) {
		return c2.compareTo(c1);
	}
}