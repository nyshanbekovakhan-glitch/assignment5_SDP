package spacerobot;

import org.junit.jupiter.api.Test;
import spacerobot.model.StationState;
import spacerobot.route.LowestEnergyRoute;
import spacerobot.route.NearestNeighborRoute;
import spacerobot.route.PriorityFirstRoute;
import spacerobot.route.RouteStrategySelector;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class RouteStrategySelectorTest {

    private final RouteStrategySelector selector = new RouteStrategySelector();

    @Test
    void alarmSelectsPriorityFirst() {
        assertInstanceOf(PriorityFirstRoute.class,
                selector.select(new StationState(80, true, false, false)));
    }

    @Test
    void lowBatterySelectsLowestEnergy() {
        assertInstanceOf(LowestEnergyRoute.class,
                selector.select(new StationState(19, false, false, false)));
    }

    @Test
    void normalSituationSelectsNearestNeighbor() {
        assertInstanceOf(NearestNeighborRoute.class,
                selector.select(new StationState(80, false, false, false)));
    }

    @Test
    void alarmWinsOverLowBattery() {
        assertInstanceOf(PriorityFirstRoute.class,
                selector.select(new StationState(5, true, false, false)));
    }

    @Test
    void batteryExactlyTwentyIsNotLow() {
        assertInstanceOf(NearestNeighborRoute.class,
                selector.select(new StationState(20, false, false, false)));
    }
}
