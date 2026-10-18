package spacerobot;

import org.junit.jupiter.api.Test;
import spacerobot.datasource.SensorDataSource;
import spacerobot.risk.LegacyRiskAssessor;
import spacerobot.risk.RiskMode;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RiskScoringBehaviorTest {

    private double score(RiskMode mode, SensorDataSource source) {
        return new LegacyRiskAssessor(source).assess(mode, "M1");
    }

    @Test
    void thresholdCalmModuleIsLowRisk() {
        assertEquals(10, score(RiskMode.THRESHOLD, FakeSensorDataSource.calm()), 0.001);
    }

    @Test
    void thresholdWarningLevel() {
        var source = new FakeSensorDataSource(20, 100, 5, List.of(1.0));
        assertEquals(60, score(RiskMode.THRESHOLD, source), 0.001);
    }

    @Test
    void thresholdDangerLevel() {
        var source = new FakeSensorDataSource(20, 100, 9, List.of(1.0));
        assertEquals(100, score(RiskMode.THRESHOLD, source), 0.001);
    }

    @Test
    void weightedCalmModule() {
        assertEquals(10, score(RiskMode.WEIGHTED, FakeSensorDataSource.calm()), 0.001);
    }

    @Test
    void weightedMaximumIsOneHundred() {
        var source = new FakeSensorDataSource(60, 80, 10, List.of(1.0));
        assertEquals(100, score(RiskMode.WEIGHTED, source), 0.001);
    }

    @Test
    void trendRisingRadiation() {
        assertEquals(58, score(RiskMode.TREND, FakeSensorDataSource.calm()), 0.001);
    }

    @Test
    void trendFlatRadiationHasNoSlopePart() {
        var source = new FakeSensorDataSource(20, 100, 2, List.of(3.0, 3.0, 3.0));
        assertEquals(18, score(RiskMode.TREND, source), 0.001);
    }
}
