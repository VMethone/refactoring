package theater;

/**
 * Calculator for pastoral plays.
 */
public class PastoralCalculator extends AbstractPerformanceCalculator {

    public PastoralCalculator(Performance performance, Play play) {
        super(performance, play);
    }

    @Override
    public int amountFor() {
        int result = Constants.PASTORAL_BASE_AMOUNT;
        if (getPerformance().getAudience() > Constants.PASTORAL_AUDIENCE_THRESHOLD) {
            result += Constants.PASTORAL_OVER_BASE_CAPACITY_PER_PERSON
                    * (getPerformance().getAudience() - Constants.PASTORAL_AUDIENCE_THRESHOLD);
        }
        return result;
    }

    @Override
    public int volumeCredits() {
        return Math.max(getPerformance().getAudience() - Constants.PASTORAL_VOLUME_CREDIT_THRESHOLD, 0)
                + getPerformance().getAudience() / 2;
    }
}
