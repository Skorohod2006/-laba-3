// поток такси, выполняющий поездки
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Taxi implements Runnable {
    private final int id;
    private Location currentlocation;
    private TaxiStatus status;
    private Order currentorder;

    private final Lock lock = new ReentrantLock();
    private final Condition newordercondition = lock.newCondition();

    public Taxi(int id, Location startlocation) {
        this.id = id;
        this.currentlocation = startlocation;
        this.status = TaxiStatus.free;
    }

    public int getid() { return id; }

    public TaxiStatus getstatus() {
        lock.lock();
        try { return status; } finally { lock.unlock(); }
    }

    public Location getlocation() {
        lock.lock();
        try { return currentlocation; } finally { lock.unlock(); }
    }

    public boolean assignorder(Order order) {
        lock.lock();
        try {
            if (this.status == TaxiStatus.free) {
                this.currentorder = order;
                this.status = TaxiStatus.to_client;
                newordercondition.signal();
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                lock.lock();
                try {
                    while (currentorder == null) {
                        newordercondition.await();
                    }
                } finally {
                    lock.unlock();
                }
                processorder();
            }
        } catch (InterruptedException e) {
            System.out.println("taxi-" + id + " завершил работу.");
        }
    }

    private void processorder() throws InterruptedException {
        System.out.println("[taxi-" + id + "] едет к клиенту " + currentorder.getstart());
        simulatemovement(currentlocation, currentorder.getstart());
        
        lock.lock();
        try {
            currentlocation = currentorder.getstart();
            status = TaxiStatus.transporting;
            System.out.println("[taxi-" + id + "] забрал клиента. едет в " + currentorder.getend());
        } finally { lock.unlock(); }

        simulatemovement(currentorder.getstart(), currentorder.getend());

        lock.lock();
        try {
            currentlocation = currentorder.getend();
            System.out.println("[taxi-" + id + "] завершил " + currentorder + ". свободен.");
            currentorder = null;
            status = TaxiStatus.free;
        } finally { lock.unlock(); }
    }

    private void simulatemovement(Location from, Location to) throws InterruptedException {
        long time = (long) (from.distanceto(to) * 100);
        Thread.sleep(Math.max(time, 1000));
    }
}
