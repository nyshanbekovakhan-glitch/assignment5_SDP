package spacerobot;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import spacerobot.model.StationModule;
import spacerobot.risk.RiskScoringStrategy;
import spacerobot.risk.ThresholdRiskScoring;
import spacerobot.risk.TrendRiskScoring;
import spacerobot.risk.WeightedRiskScoring;
import spacerobot.route.LowestEnergyRoute;
import spacerobot.route.NearestNeighborRoute;
import spacerobot.route.PriorityFirstRoute;
import spacerobot.route.RouteStrategy;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AllStrategiesParameterizedTest {

    static Stream<RiskScoringStrategy> riskStrategies() {
        List<FakeSensorDataSource> sources = List.of(
                FakeSensorDataSource.calm(),
                new FakeSensorDataSource(80, 50, 50, List.of(0.0, 50.0)),
                new FakeSensorDataSource(-50, 150, 0, List.of(0.0, 0.0)));
        return sources.stream().flatMap(source -> Stream.of(
                new ThresholdRiskScoring(source),
                new WeightedRiskScoring(source),
                new TrendRiskScoring(source)));
    }

    static Stream<RouteStrategy> routeStrategies() {
        return Stream.of(new NearestNeighborRoute(), new PriorityFirstRoute(), new LowestEnergyRoute());
    }

    @ParameterizedTest
    @MethodSource("riskStrategies")
    void riskScoreIsAlwaysBetweenZeroAndHundred(RiskScoringStrategy strategy) {
        double score = strategy.score("M1");
        assertTrue(score >= 0 && score <= 100, "score out of range: " + score);
    }

    @ParameterizedTest
    @MethodSource("routeStrategies")
    void routeVisitsEveryModuleExactlyOnceWithoutStart(RouteStrategy strategy) {
        StationModule dock = new StationModule("Dock", 0, 0, 1, 0);
        List<StationModule> modules = List.of(
                dock,
                new StationModule("A", 2, 0, 1, 20),
                new StationModule("B", 4, 0, 3, 1),
                new StationModule("C", 1, 5, 5, 10));

        List<StationModule> route = strategy.planRoute(modules, dock);

        assertEquals(3, route.size());
        assertEquals(3, new HashSet<>(route).size());
        assertTrue(!route.contains(dock));
    }
}
