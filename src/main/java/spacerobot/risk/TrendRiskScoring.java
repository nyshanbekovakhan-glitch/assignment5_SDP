package spacerobot.risk;

import spacerobot.datasource.SensorDataSource;

import java.util.List;

public class TrendRiskScoring implements RiskScoringStrategy {

    private static final int WINDOW = 5;

    private final SensorDataSource source;

    public TrendRiskScoring(SensorDataSource source) {
        this.source = source;
    }

    @Override
    public double score(String moduleId) {
        List<Double> readings = source.recentRadiation(moduleId, WINDOW);
        double last = readings.get(readings.size() - 1);
        double slope = readings.size() < 2
                ? 0
                : (last - readings.get(0)) / (readings.size() - 1);
        return RiskMath.clamp01(last / 10.0) * 60 + RiskMath.clamp01(slope) * 40;
    }
}
