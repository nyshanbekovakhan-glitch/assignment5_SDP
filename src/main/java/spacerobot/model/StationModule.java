package spacerobot.model;

public record StationModule(String id, double x, double y, int priority, double hatchEnergy) {

    public double distanceTo(StationModule other) {
        return Math.hypot(x - other.x, y - other.y);
    }
}
