package spacerobot.risk;

import spacerobot.datasource.SensorDataSource;

public class WeightedRiskScoring implements RiskScoringStrategy {

    private final SensorDataSource source;

    public WeightedRiskScoring(SensorDataSource source) {
        this.source = source;
    }

    @Override
    public double score(String moduleId) {
        double radiationPart = RiskMath.clamp01(source.radiation(moduleId) / 10.0);
        double temperaturePart = RiskMath.clamp01((source.temperature(moduleId) - 20) / 40.0);
        double pressurePart = RiskMath.clamp01((100 - source.pressure(moduleId)) / 20.0);
        return 100 * (0.5 * radiationPart + 0.3 * temperaturePart + 0.2 * pressurePart);
    }
}
