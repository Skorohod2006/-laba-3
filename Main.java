// точка входа и запуск системы
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        BlockingQueue<Order> orderqueue = new ArrayBlockingQueue<>(10);
        TaxiFleet fleet = new TaxiFleet();
        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 1; i <= 3; i++) {
            Taxi taxi = new Taxi(i, new Location(0, 0));
            fleet.addtaxi(taxi);
            executor.execute(taxi);
        }

        executor.execute(new Dispatcher(orderqueue, fleet));
        executor.execute(new ClientGenerator(orderqueue));

        try { Thread.sleep(20000); } catch (InterruptedException e) {}
        executor.shutdownNow();
    }
}
