package spacerobot.route;

import spacerobot.model.StationState;

public class RouteStrategySelector {

    private static final int LOW_BATTERY_PERCENT = 20;

    public RouteStrategy select(StationState state) {
        if (state.alarmActive()) {
            return new PriorityFirstRoute();
        }
        if (state.batteryPercent() < LOW_BATTERY_PERCENT) {
            return new LowestEnergyRoute();
        }
        return new NearestNeighborRoute();
    }
}
