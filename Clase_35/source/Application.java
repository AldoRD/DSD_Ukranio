import java.util.Arrays;
import java.util.List;

public class Application {
    private static final String WORKER_ADDRESS_1 = "http://localhost:8081/search";
    private static final String WORKER_ADDRESS_2 = "http://localhost:8082/search";

    public static void main(String[] args) {
        Aggregator aggregator = new Aggregator();
        String task1 = "175760,IPN";
        String task2 = "1757600,SAL";
        String task3 = "700000,MAS";

        List<String> results = aggregator.sendTasksToWorkers(Arrays.asList(WORKER_ADDRESS_1, WORKER_ADDRESS_2),
                Arrays.asList(task1, task2, task3));

        for (String result : results) {
            System.out.println("\t" + result);
        }
    }
}