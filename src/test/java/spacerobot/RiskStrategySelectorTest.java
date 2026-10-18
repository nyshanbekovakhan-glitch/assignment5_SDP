package spacerobot;

import org.junit.jupiter.api.Test;
import spacerobot.model.StationState;
import spacerobot.risk.RiskStrategySelector;
import spacerobot.risk.ThresholdRiskScoring;
import spacerobot.risk.TrendRiskScoring;
import spacerobot.risk.WeightedRiskScoring;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class RiskStrategySelectorTest {

    private final RiskStrategySelector selector = new RiskStrategySelector(FakeSensorDataSource.calm());

    @Test
    void radiationStormSelectsThreshold() {
        assertInstanceOf(ThresholdRiskScoring.class,
                selector.select(new StationState(80, false, true, false)));
    }

    @Test
    void routineInspectionSelectsWeighted() {
        assertInstanceOf(WeightedRiskScoring.class,
                selector.select(new StationState(80, false, false, true)));
    }

    @Test
    void otherwiseSelectsTrend() {
        assertInstanceOf(TrendRiskScoring.class,
                selector.select(new StationState(80, false, false, false)));
    }

    @Test
    void stormWinsOverRoutineInspection() {
        assertInstanceOf(ThresholdRiskScoring.class,
                selector.select(new StationState(80, false, true, true)));
    }
}
