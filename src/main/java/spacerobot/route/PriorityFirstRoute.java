package spacerobot.route;

import spacerobot.model.StationModule;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PriorityFirstRoute implements RouteStrategy {

    @Override
    public List<StationModule> planRoute(List<StationModule> modules, StationModule start) {
        List<StationModule> route = new ArrayList<>(modules);
        route.remove(start);
        route.sort(Comparator.comparingInt(StationModule::priority).reversed()
                .thenComparingDouble(module -> start.distanceTo(module)));
        return route;
    }
}
