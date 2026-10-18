package spacerobot;

import org.junit.jupiter.api.Test;
import spacerobot.risk.WeightedRiskScoring;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeightedRiskScoringTest {

    @Test
    void onlyTemperatureContributesThirtyPercent() {
        var source = new FakeSensorDataSource(60, 100, 0, List.of(0.0));
        assertEquals(30, new WeightedRiskScoring(source).score("M1"), 0.001);
    }

    @Test
    void onlyPressureContributesTwentyPercent() {
        var source = new FakeSensorDataSource(20, 80, 0, List.of(0.0));
        assertEquals(20, new WeightedRiskScoring(source).score("M1"), 0.001);
    }

    @Test
    void extremeValuesAreClampedToZero() {
        var source = new FakeSensorDataSource(-50, 150, 0, List.of(0.0));
        assertEquals(0, new WeightedRiskScoring(source).score("M1"), 0.001);
    }
}
