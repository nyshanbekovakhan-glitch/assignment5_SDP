package spacerobot.route;

import spacerobot.model.StationModule;

import java.util.List;

public interface RouteStrategy {

    List<StationModule> planRoute(List<StationModule> modules, StationModule start);
}
