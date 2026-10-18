package spacerobot.risk;

public class RiskAssessor {

    private RiskScoringStrategy strategy;

    public RiskAssessor(RiskScoringStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(RiskScoringStrategy strategy) {
        this.strategy = strategy;
    }

    public double assess(String moduleId) {
        return strategy.score(moduleId);
    }
}
