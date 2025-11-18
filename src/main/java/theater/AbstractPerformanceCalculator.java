package theater;

/**
 * Base calculator for a performance. Subclasses implement type-specific behaviour.
 */
public class AbstractPerformanceCalculator {

    private final Performance performance;
    private final Play play;

    public AbstractPerformanceCalculator(Performance performance, Play play) {
        this.performance = performance;
        this.play = play;
    }

    public Performance getPerformance() {
        return performance;
    }

    public Play getPlay() {
        return play;
    }

    public int amountFor() {
        // default implementation mirrors original switch; subclasses may override
        switch (play.getType()) {
            case "tragedy":
                int result = Constants.TRAGEDY_BASE_AMOUNT;
                if (performance.getAudience() > Constants.TRAGEDY_AUDIENCE_THRESHOLD) {
                    result += Constants.TRAGEDY_OVER_BASE_CAPACITY_PER_PERSON * (performance.getAudience() - Constants.TRAGEDY_AUDIENCE_THRESHOLD);
                }
                return result;
            case "comedy":
                int res = Constants.COMEDY_BASE_AMOUNT;
                if (performance.getAudience() > Constants.COMEDY_AUDIENCE_THRESHOLD) {
                    res += Constants.COMEDY_OVER_BASE_CAPACITY_AMOUNT + (Constants.COMEDY_OVER_BASE_CAPACITY_PER_PERSON * (performance.getAudience() - Constants.COMEDY_AUDIENCE_THRESHOLD));
                }
                res += Constants.COMEDY_AMOUNT_PER_AUDIENCE * performance.getAudience();
                return res;
            case "history":
                int r = Constants.HISTORY_BASE_AMOUNT;
                if (performance.getAudience() > Constants.HISTORY_AUDIENCE_THRESHOLD) {
                    r += Constants.HISTORY_OVER_BASE_CAPACITY_PER_PERSON * (performance.getAudience() - Constants.HISTORY_AUDIENCE_THRESHOLD);
                }
                return r;
            case "pastoral":
                int rr = Constants.PASTORAL_BASE_AMOUNT;
                if (performance.getAudience() > Constants.PASTORAL_AUDIENCE_THRESHOLD) {
                    rr += Constants.PASTORAL_OVER_BASE_CAPACITY_PER_PERSON * (performance.getAudience() - Constants.PASTORAL_AUDIENCE_THRESHOLD);
                }
                return rr;
            default:
                throw new RuntimeException(String.format("unknown type: %s", play.getType()));
        }
    }

    public int volumeCredits() {
        int result = Math.max(performance.getAudience() - Constants.BASE_VOLUME_CREDIT_THRESHOLD, 0);
        if ("comedy".equals(play.getType())) {
            result += performance.getAudience() / Constants.COMEDY_EXTRA_VOLUME_FACTOR;
        }
        return result;
    }

    public static AbstractPerformanceCalculator createPerformanceCalculator(Performance performance, Play play) {
        switch (play.getType()) {
            case "tragedy":
                return new TragedyCalculator(performance, play);
            case "comedy":
                return new ComedyCalculator(performance, play);
            case "history":
                return new HistoryCalculator(performance, play);
            case "pastoral":
                return new PastoralCalculator(performance, play);
            default:
                return new AbstractPerformanceCalculator(performance, play);
        }
    }
}
