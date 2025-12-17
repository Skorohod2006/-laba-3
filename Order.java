// модель заказа клиента
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order {
    private static int idcounter = 1;
    private final int id;
    private final Location start;
    private final Location end;
    private final LocalDateTime timestamp;

    public Order(Location start, Location end) {
        this.id = idcounter++;
        this.start = start;
        this.end = end;
        this.timestamp = LocalDateTime.now();
    }

    public int getid() { return id; }
    public Location getstart() { return start; }
    public Location getend() { return end; }

    @Override
    public String toString() {
        // добавлено форматирование времени, чтобы поле timestamp "работало"
        String time = timestamp.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return String.format("заказ #%d [%s] (%s -> %s)", id, time, start, end);
    }
}
