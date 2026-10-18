package spacerobot.datasource;

import java.util.List;

public interface SensorDataSource {

    double temperature(String moduleId);

    double pressure(String moduleId);

    double radiation(String moduleId);

    List<Double> recentRadiation(String moduleId, int count);
}
