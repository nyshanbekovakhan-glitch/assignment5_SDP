package spacerobot.risk;

import spacerobot.datasource.SensorDataSource;
import spacerobot.model.StationState;

public class RiskStrategySelector {

    private final SensorDataSource source;

    public RiskStrategySelector(SensorDataSource source) {
        this.source = source;
    }

    public RiskScoringStrategy select(StationState state) {
        if (state.radiationStorm()) {
            return new ThresholdRiskScoring(source);
        }
        if (state.routineInspection()) {
            return new WeightedRiskScoring(source);
        }
        return new TrendRiskScoring(source);
    }
}
