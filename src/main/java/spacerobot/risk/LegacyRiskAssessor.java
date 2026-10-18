package spacerobot.risk;

import spacerobot.datasource.SensorDataSource;

import java.util.List;

public class LegacyRiskAssessor {

    private final SensorDataSource source;

    public LegacyRiskAssessor(SensorDataSource source) {
        this.source = source;
    }

    public double assess(RiskMode mode, String moduleId) {
        switch (mode) {
            case THRESHOLD:
                return thresholdScore(moduleId);
            case WEIGHTED:
                return weightedScore(moduleId);
            case TREND:
                return trendScore(moduleId);
            default:
                throw new IllegalArgumentException("Unknown mode: " + mode);
        }
    }

    private double thresholdScore(String id) {
        double radiation = source.radiation(id);
        double temperature = source.temperature(id);
        double pressure = source.pressure(id);
        if (radiation >= 8 || temperature >= 60 || pressure <= 80) {
            return 100;
        }
        if (radiation >= 4 || temperature >= 45 || pressure <= 90) {
            return 60;
        }
        return 10;
    }

    private double weightedScore(String id) {
        double radiationPart = clamp(source.radiation(id) / 10.0);
        double temperaturePart = clamp((source.temperature(id) - 20) / 40.0);
        double pressurePart = clamp((100 - source.pressure(id)) / 20.0);
        return 100 * (0.5 * radiationPart + 0.3 * temperaturePart + 0.2 * pressurePart);
    }

    private double trendScore(String id) {
        List<Double> readings = source.recentRadiation(id, 5);
        double last = readings.get(readings.size() - 1);
        double slope = readings.size() < 2
                ? 0
                : (last - readings.get(0)) / (readings.size() - 1);
        return clamp(last / 10.0) * 60 + clamp(slope) * 40;
    }

    private double clamp(double value) {
        return Math.max(0, Math.min(1, value));
    }
}
