package spacerobot.route;

import spacerobot.model.StationModule;

import java.util.ArrayList;
import java.util.List;

public class LowestEnergyRoute implements RouteStrategy {

    private static final double ENERGY_PER_METER = 2.0;

    @Override
    public List<StationModule> planRoute(List<StationModule> modules, StationModule start) {
        List<StationModule> remaining = new ArrayList<>(modules);
        remaining.remove(start);
        List<StationModule> route = new ArrayList<>();
        StationModule current = start;
        while (!remaining.isEmpty()) {
            StationModule next = cheapest(current, remaining);
            route.add(next);
            remaining.remove(next);
            current = next;
        }
        return route;
    }

    private StationModule cheapest(StationModule from, List<StationModule> candidates) {
        StationModule best = candidates.get(0);
        for (StationModule candidate : candidates) {
            if (cost(from, candidate) < cost(from, best)) {
                best = candidate;
            }
        }
        return best;
    }

    private double cost(StationModule from, StationModule to) {
        return from.distanceTo(to) * ENERGY_PER_METER + to.hatchEnergy();
    }
}
