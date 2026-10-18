package spacerobot;

import org.junit.jupiter.api.Test;
import spacerobot.model.StationModule;
import spacerobot.route.LowestEnergyRoute;
import spacerobot.route.NearestNeighborRoute;
import spacerobot.route.PriorityFirstRoute;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RouteStrategiesTest {

    private final StationModule dock = new StationModule("Dock", 0, 0, 1, 0);
    private final StationModule a = new StationModule("A", 2, 0, 1, 20);
    private final StationModule b = new StationModule("B", 4, 0, 3, 1);
    private final StationModule c = new StationModule("C", 1, 5, 5, 10);
    private final List<StationModule> modules = List.of(dock, a, b, c);

    private List<String> ids(List<StationModule> route) {
        return route.stream().map(StationModule::id).toList();
    }

    @Test
    void nearestNeighborVisitsClosestFirst() {
        assertEquals(List.of("A", "B", "C"), ids(new NearestNeighborRoute().planRoute(modules, dock)));
    }

    @Test
    void priorityFirstVisitsMostCriticalFirst() {
        assertEquals(List.of("C", "B", "A"), ids(new PriorityFirstRoute().planRoute(modules, dock)));
    }

    @Test
    void lowestEnergyAvoidsHeavyHatchFirst() {
        assertEquals(List.of("B", "C", "A"), ids(new LowestEnergyRoute().planRoute(modules, dock)));
    }
}
