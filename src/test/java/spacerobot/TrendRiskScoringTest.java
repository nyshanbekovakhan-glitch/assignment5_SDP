package spacerobot;

import org.junit.jupiter.api.Test;
import spacerobot.risk.TrendRiskScoring;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrendRiskScoringTest {

    @Test
    void singleReadingHasNoSlope() {
        var source = new FakeSensorDataSource(20, 100, 0, List.of(5.0));
        assertEquals(30, new TrendRiskScoring(source).score("M1"), 0.001);
    }

    @Test
    void fastRisingRadiationGivesMaximum() {
        var source = new FakeSensorDataSource(20, 100, 0, List.of(0.0, 50.0));
        assertEquals(100, new TrendRiskScoring(source).score("M1"), 0.001);
    }

    @Test
    void fallingRadiationHasNoSlopePart() {
        var source = new FakeSensorDataSource(20, 100, 0, List.of(5.0, 3.0));
        assertEquals(18, new TrendRiskScoring(source).score("M1"), 0.001);
    }
}
