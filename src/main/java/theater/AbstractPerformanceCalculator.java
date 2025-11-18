package theater;

/**
 * Abstract base calculator for a performance. Subclasses implement type-specific behaviour.
 */
public abstract class AbstractPerformanceCalculator {

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

    /**
     * Calculate the amount for this performance.
     * Subclasses may override to provide type-specific calculation.
     * @return amount in cents
     */
    public int amountFor() {
        int result = 0;
        final String type = play.getType();
        if ("tragedy".equals(type)) {
            result = Constants.TRAGEDY_BASE_AMOUNT;
            if (performance.getAudience() > Constants.TRAGEDY_AUDIENCE_THRESHOLD) {
                result += Constants.TRAGEDY_OVER_BASE_CAPACITY_PER_PERSON
                        * (performance.getAudience() - Constants.TRAGEDY_AUDIENCE_THRESHOLD);
            }
        } else if ("comedy".equals(type)) {
            result = Constants.COMEDY_BASE_AMOUNT;
            if (performance.getAudience() > Constants.COMEDY_AUDIENCE_THRESHOLD) {
                result += Constants.COMEDY_OVER_BASE_CAPACITY_AMOUNT
                        + (Constants.COMEDY_OVER_BASE_CAPACITY_PER_PERSON
                        * (performance.getAudience() - Constants.COMEDY_AUDIENCE_THRESHOLD));
            }
            result += Constants.COMEDY_AMOUNT_PER_AUDIENCE * performance.getAudience();
        } else if ("history".equals(type)) {
            result = Constants.HISTORY_BASE_AMOUNT;
            if (performance.getAudience() > Constants.HISTORY_AUDIENCE_THRESHOLD) {
                result += Constants.HISTORY_OVER_BASE_CAPACITY_PER_PERSON
                        * (performance.getAudience() - Constants.HISTORY_AUDIENCE_THRESHOLD);
            }
        } else if ("pastoral".equals(type)) {
            result = Constants.PASTORAL_BASE_AMOUNT;
            if (performance.getAudience() > Constants.PASTORAL_AUDIENCE_THRESHOLD) {
                result += Constants.PASTORAL_OVER_BASE_CAPACITY_PER_PERSON
                        * (performance.getAudience() - Constants.PASTORAL_AUDIENCE_THRESHOLD);
            }
        } else {
            throw new RuntimeException(String.format("unknown type: %s", play.getType()));
        }
        return result;
    }

    /**
     * Calculate volume credits for this performance. Subclasses may override.
     * @return volume credits
     */
    public int volumeCredits() {
        int result = Math.max(performance.getAudience() - Constants.BASE_VOLUME_CREDIT_THRESHOLD, 0);
        if ("comedy".equals(play.getType())) {
            result += performance.getAudience() / Constants.COMEDY_EXTRA_VOLUME_FACTOR;
        }
        return result;
    }

    /**
     * Factory to create appropriate calculator subtype for the given play.
     * @param performance performance
     * @param play play
     * @return a calculator instance
     */
    public static AbstractPerformanceCalculator createPerformanceCalculator(final Performance performance, final Play play) {
        final String type = play.getType();
        if ("tragedy".equals(type)) {
            return new TragedyCalculator(performance, play);
        }
        if ("comedy".equals(type)) {
            return new ComedyCalculator(performance, play);
        }
        if ("history".equals(type)) {
            return new HistoryCalculator(performance, play);
        }
        if ("pastoral".equals(type)) {
            return new PastoralCalculator(performance, play);
        }
        return new TragedyCalculator(performance, play);
    }
}
