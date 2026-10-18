package spacerobot;

import spacerobot.datasource.SensorDataSource;
import spacerobot.datasource.SimulatedSensorDataSource;
import spacerobot.model.StationModule;
import spacerobot.model.StationState;
import spacerobot.risk.RiskAssessor;
import spacerobot.risk.RiskScoringStrategy;
import spacerobot.risk.RiskStrategySelector;
import spacerobot.risk.ThresholdRiskScoring;
import spacerobot.risk.TrendRiskScoring;
import spacerobot.risk.WeightedRiskScoring;
import spacerobot.route.LowestEnergyRoute;
import spacerobot.route.NearestNeighborRoute;
import spacerobot.route.PriorityFirstRoute;
import spacerobot.route.RoutePlanner;
import spacerobot.route.RouteStrategy;
import spacerobot.route.RouteStrategySelector;

import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        StationModule dock = new StationModule("Dock", 0, 0, 1, 0);
        List<StationModule> modules = List.of(
                dock,
                new StationModule("A-Lab", 2, 0, 1, 20),
                new StationModule("B-Hub", 4, 0, 3, 1),
                new StationModule("C-Reactor", 1, 5, 5, 10));

        routeDemo(modules, dock);
        riskDemo(modules);
    }

    private static void routeDemo(List<StationModule> modules, StationModule dock) {
        System.out.println("=== 1. Same input, all route strategies ===");
        List<RouteStrategy> all = List.of(
                new NearestNeighborRoute(),
                new PriorityFirstRoute(),
                new LowestEnergyRoute());
        RoutePlanner planner = new RoutePlanner(all.get(0));
        for (RouteStrategy strategy : all) {
            planner.setStrategy(strategy);
            System.out.printf("%-20s %s%n", strategy.getClass().getSimpleName(),
                    names(planner.plan(modules, dock)));
        }

        System.out.println();
        System.out.println("=== 2. Manual choice vs automatic choice ===");
        RoutePlanner manual = new RoutePlanner(new PriorityFirstRoute());
        System.out.printf("Manual (PriorityFirstRoute): %s%n", names(manual.plan(modules, dock)));

        RouteStrategySelector selector = new RouteStrategySelector();
        List<StationState> situations = List.of(
                new StationState(80, false, false, true),
                new StationState(15, false, false, true),
                new StationState(60, true, false, false));
        RoutePlanner auto = new RoutePlanner(new NearestNeighborRoute());
        for (StationState state : situations) {
            RouteStrategy chosen = selector.select(state);
            auto.setStrategy(chosen);
            System.out.printf("Auto battery=%d%% alarm=%b -> %s: %s%n",
                    state.batteryPercent(), state.alarmActive(),
                    chosen.getClass().getSimpleName(), names(auto.plan(modules, dock)));
        }
    }

    private static void riskDemo(List<StationModule> modules) {
        System.out.println();
        System.out.println("=== 3. Risk scoring: all strategies, same module ===");
        String target = modules.get(3).id();
        List<RiskScoringStrategy> strategies = List.of(
                new ThresholdRiskScoring(new SimulatedSensorDataSource(7)),
                new WeightedRiskScoring(new SimulatedSensorDataSource(7)),
                new TrendRiskScoring(new SimulatedSensorDataSource(7)));
        RiskAssessor assessor = new RiskAssessor(strategies.get(0));
        for (RiskScoringStrategy strategy : strategies) {
            assessor.setStrategy(strategy);
            System.out.printf("%-22s %.1f%n", strategy.getClass().getSimpleName(), assessor.assess(target));
        }

        System.out.println();
        System.out.println("=== 4. Automatic risk strategy and runtime switch ===");
        SensorDataSource source = new SimulatedSensorDataSource(7);
        RiskStrategySelector selector = new RiskStrategySelector(source);
        StationState routine = new StationState(80, false, false, true);
        StationState storm = new StationState(80, false, true, false);

        RiskAssessor live = new RiskAssessor(selector.select(routine));
        System.out.printf("Routine -> %.1f%n", live.assess(target));
        live.setStrategy(selector.select(storm));
        System.out.printf("Storm   -> %.1f%n", live.assess(target));
    }

    private static String names(List<StationModule> route) {
        return route.stream().map(StationModule::id).collect(Collectors.joining(" -> "));
    }
}
