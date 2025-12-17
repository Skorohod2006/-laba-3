// поток для распределения заказов
import java.util.concurrent.BlockingQueue;

public class Dispatcher implements Runnable {
    private final BlockingQueue<Order> orderqueue;
    private final TaxiFleet taxifleet;

    public Dispatcher(BlockingQueue<Order> orderqueue, TaxiFleet taxifleet) {
        this.orderqueue = orderqueue;
        this.taxifleet = taxifleet;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Order order = orderqueue.take();
                boolean assigned = false;
                while (!assigned) {
                    Taxi taxi = taxifleet.findnearestfreetaxi(order.getstart());
                    if (taxi != null && taxi.assignorder(order)) {
                        assigned = true;
                        System.out.println("[dispatcher] назначил заказ " + order.getid() + " на taxi-" + taxi.getid());
                    } else {
                        Thread.sleep(500); 
                    }
                }
            }
        } catch (InterruptedException e) {
            System.out.println("диспетчер остановлен.");
        }
    }
}
