package spacerobot.risk;

import spacerobot.datasource.SensorDataSource;

public class ThresholdRiskScoring implements RiskScoringStrategy {

    private final SensorDataSource source;

    public ThresholdRiskScoring(SensorDataSource source) {
        this.source = source;
    }

    @Override
    public double score(String moduleId) {
        double radiation = source.radiation(moduleId);
        double temperature = source.temperature(moduleId);
        double pressure = source.pressure(moduleId);
        if (radiation >= 8 || temperature >= 60 || pressure <= 80) {
            return 100;
        }
        if (radiation >= 4 || temperature >= 45 || pressure <= 90) {
            return 60;
        }
        return 10;
    }
}
