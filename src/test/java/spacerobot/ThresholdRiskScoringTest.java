package spacerobot;

import org.junit.jupiter.api.Test;
import spacerobot.risk.ThresholdRiskScoring;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThresholdRiskScoringTest {

    @Test
    void lowPressureIsDanger() {
        var source = new FakeSensorDataSource(20, 75, 1, List.of(1.0));
        assertEquals(100, new ThresholdRiskScoring(source).score("M1"), 0.001);
    }

    @Test
    void highTemperatureIsWarning() {
        var source = new FakeSensorDataSource(50, 100, 1, List.of(1.0));
        assertEquals(60, new ThresholdRiskScoring(source).score("M1"), 0.001);
    }

    @Test
    void calmModuleIsSafe() {
        assertEquals(10, new ThresholdRiskScoring(FakeSensorDataSource.calm()).score("M1"), 0.001);
    }
}
