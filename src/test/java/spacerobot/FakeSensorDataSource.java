package spacerobot;

import spacerobot.datasource.SensorDataSource;

import java.util.List;

public class FakeSensorDataSource implements SensorDataSource {

    private final double temperature;
    private final double pressure;
    private final double radiation;
    private final List<Double> recent;

    public FakeSensorDataSource(double temperature, double pressure, double radiation, List<Double> recent) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.radiation = radiation;
        this.recent = recent;
    }

    public static FakeSensorDataSource calm() {
        return new FakeSensorDataSource(20, 100, 2, List.of(1.0, 2.0, 3.0));
    }

    @Override
    public double temperature(String moduleId) {
        return temperature;
    }

    @Override
    public double pressure(String moduleId) {
        return pressure;
    }

    @Override
    public double radiation(String moduleId) {
        return radiation;
    }

    @Override
    public List<Double> recentRadiation(String moduleId, int count) {
        return recent;
    }
}
