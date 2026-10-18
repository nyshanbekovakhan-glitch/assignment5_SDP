package spacerobot.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulatedSensorDataSource implements SensorDataSource {

    private final Random random;

    public SimulatedSensorDataSource(long seed) {
        this.random = new Random(seed);
    }

    @Override
    public double temperature(String moduleId) {
        return 18 + random.nextDouble() * 45;
    }

    @Override
    public double pressure(String moduleId) {
        return 78 + random.nextDouble() * 24;
    }

    @Override
    public double radiation(String moduleId) {
        return random.nextDouble() * 10;
    }

    @Override
    public List<Double> recentRadiation(String moduleId, int count) {
        List<Double> values = new ArrayList<>();
        double current = random.nextDouble() * 5;
        for (int i = 0; i < count; i++) {
            values.add(current);
            current += random.nextDouble() * 1.5;
        }
        return values;
    }
}
