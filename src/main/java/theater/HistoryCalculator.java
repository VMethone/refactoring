package theater;

public class HistoryCalculator extends AbstractPerformanceCalculator {

    public HistoryCalculator(Performance performance, Play play) {
        super(performance, play);
    }

    @Override
    public int amountFor() {
        int result = Constants.HISTORY_BASE_AMOUNT;
        if (getPerformance().getAudience() > Constants.HISTORY_AUDIENCE_THRESHOLD) {
            result += Constants.HISTORY_OVER_BASE_CAPACITY_PER_PERSON * (getPerformance().getAudience() - Constants.HISTORY_AUDIENCE_THRESHOLD);
        }
        return result;
    }

    @Override
    public int volumeCredits() {
        return Math.max(getPerformance().getAudience() - Constants.HISTORY_VOLUME_CREDIT_THRESHOLD, 0);
    }
}
