package ca.bazlur.loom.lab4;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.*;

public class ExecutorsExample {
    public static void main(String[] args) {
        try (var threadPool = Executors.newFixedThreadPool(5)) {
            Response response = handle(threadPool);

            System.out.println(response);

            TimeUnit.HOURS.sleep(1);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Response handle(ExecutorService executorService) throws ExecutionException, InterruptedException {
        Future<String> userFuture = executorService.submit(ExecutorsExample::findUser);
        Future<Integer> orderFuture = executorService.submit(ExecutorsExample::fetchOrder);

        String theUser = userFuture.get();
        int theOrder = orderFuture.get();
        return new Response(theUser, theOrder);
    }

    private static int fetchOrder() {
        throw new RuntimeException("Order not found");
    }

    private static String findUser() {
        throw new RuntimeException("User not found");
    }
}

record Response(String user, int order) {

}
