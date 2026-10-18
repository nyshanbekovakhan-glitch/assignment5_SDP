package spacerobot.model;

public record StationState(int batteryPercent,
                           boolean alarmActive,
                           boolean radiationStorm,
                           boolean routineInspection) {
}
