// поток, создающий новые заказы
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class ClientGenerator implements Runnable {
    private final BlockingQueue<Order> orderqueue;
    private final Random random = new Random();

    public ClientGenerator(BlockingQueue<Order> orderqueue) {
        this.orderqueue = orderqueue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Location start = new Location(random.nextInt(20), random.nextInt(20));
                Location end = new Location(random.nextInt(20), random.nextInt(20));
                Order order = new Order(start, end);
                
                orderqueue.put(order);
                System.out.println("[generator] новый " + order);
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            System.out.println("генератор остановлен.");
        }
    }
}
