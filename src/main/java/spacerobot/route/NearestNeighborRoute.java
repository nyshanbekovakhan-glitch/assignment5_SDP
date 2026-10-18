package spacerobot.route;

import spacerobot.model.StationModule;

import java.util.ArrayList;
import java.util.List;

public class NearestNeighborRoute implements RouteStrategy {

    @Override
    public List<StationModule> planRoute(List<StationModule> modules, StationModule start) {
        List<StationModule> remaining = new ArrayList<>(modules);
        remaining.remove(start);
        List<StationModule> route = new ArrayList<>();
        StationModule current = start;
        while (!remaining.isEmpty()) {
            StationModule next = nearest(current, remaining);
            route.add(next);
            remaining.remove(next);
            current = next;
        }
        return route;
    }

    private StationModule nearest(StationModule from, List<StationModule> candidates) {
        StationModule best = candidates.get(0);
        for (StationModule candidate : candidates) {
            if (from.distanceTo(candidate) < from.distanceTo(best)) {
                best = candidate;
            }
        }
        return best;
    }
}
