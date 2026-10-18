package spacerobot.route;

import spacerobot.model.StationModule;

import java.util.List;

public class RoutePlanner {

    private RouteStrategy strategy;

    public RoutePlanner(RouteStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(RouteStrategy strategy) {
        this.strategy = strategy;
    }

    public List<StationModule> plan(List<StationModule> modules, StationModule start) {
        return strategy.planRoute(modules, start);
    }
}
