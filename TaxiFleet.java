// управление списком всех такси
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TaxiFleet {
    private final List<Taxi> taxis = new CopyOnWriteArrayList<>();

    public void addtaxi(Taxi taxi) { taxis.add(taxi); }

    public Taxi findnearestfreetaxi(Location orderlocation) {
        Taxi nearest = null;
        double mindistance = Double.MAX_VALUE;

        for (Taxi taxi : taxis) {
            if (taxi.getstatus() == TaxiStatus.free) {
                double dist = taxi.getlocation().distanceto(orderlocation);
                if (dist < mindistance) {
                    mindistance = dist;
                    nearest = taxi;
                }
            }
        }
        return nearest;
    }
}
