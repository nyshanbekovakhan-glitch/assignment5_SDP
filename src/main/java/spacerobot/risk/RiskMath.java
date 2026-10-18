package spacerobot.risk;

final class RiskMath {

    private RiskMath() {
    }

    static double clamp01(double value) {
        return Math.max(0, Math.min(1, value));
    }
}
