// класс для хранения и расчета координат
public class Location {
    private final int x;
    private final int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getx() { return x; }
    public int gety() { return y; }

    public double distanceto(Location other) {
        int dx = this.x - other.x;
        int dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() {
        return String.format("[%d; %d]", x, y);
    }
}
