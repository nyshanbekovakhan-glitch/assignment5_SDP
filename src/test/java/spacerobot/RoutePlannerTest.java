package spacerobot;

import org.junit.jupiter.api.Test;
import spacerobot.model.StationModule;
import spacerobot.route.NearestNeighborRoute;
import spacerobot.route.PriorityFirstRoute;
import spacerobot.route.RoutePlanner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class RoutePlannerTest {

    @Test
    void setStrategyChangesBehaviorAtRuntime() {
        StationModule dock = new StationModule("Dock", 0, 0, 1, 0);
        List<StationModule> modules = List.of(
                dock,
                new StationModule("A", 2, 0, 1, 20),
                new StationModule("B", 4, 0, 3, 1),
                new StationModule("C", 1, 5, 5, 10));

        RoutePlanner planner = new RoutePlanner(new NearestNeighborRoute());
        List<StationModule> before = planner.plan(modules, dock);
        planner.setStrategy(new PriorityFirstRoute());
        List<StationModule> after = planner.plan(modules, dock);

        assertNotEquals(before, after);
        assertEquals("C", after.get(0).id());
    }
}
