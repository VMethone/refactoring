package theater;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

/**
 * This class generates a statement for a given invoice of performances.
 */
public class StatementPrinter {
    private final Invoice invoice;
    private final Map<String, Play> plays;

    public StatementPrinter(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
    }

    protected Invoice getInvoice() {
        return invoice;
    }

    protected Map<String, Play> getPlays() {
        return plays;
    }

    /**
     * Returns a formatted statement of the invoice associated with this printer.
     * @return the formatted statement
     * @throws RuntimeException if one of the play types is not known
     */
    public String statement() {
        final StatementData statementData = new StatementData(getInvoice(), getPlays());
        return renderPlainText(statementData);
    }

    private String renderPlainText(StatementData statementData) {
        final StringBuilder result = new StringBuilder("Statement for " + statementData.getCustomer() + System.lineSeparator());
        for (final PerformanceData pd : statementData.getPerformances()) {
            result.append(String.format("  %s: %s (%s seats)%n", pd.getName(), usd(pd.amountFor()), pd.getAudience()));
        }
        result.append(String.format("Amount owed is %s%n", usd(statementData.totalAmount())));
        result.append(String.format("You earned %s credits%n", statementData.volumeCredits()));
        return result.toString();
    }

    protected Play getPlay(Performance performance) {
        return plays.get(performance.getPlayID());
    }

    protected int getAmount(Performance performance) {
        Play play = getPlay(performance);
        int result = 0;
        switch (play.getType()) {
            case "tragedy":
                result = Constants.TRAGEDY_BASE_AMOUNT;
                if (performance.getAudience() > Constants.TRAGEDY_AUDIENCE_THRESHOLD) {
                    result += Constants.TRAGEDY_OVER_BASE_CAPACITY_PER_PERSON
                            * (performance.getAudience() - Constants.TRAGEDY_AUDIENCE_THRESHOLD);
                }
                break;
            case "comedy":
                result = Constants.COMEDY_BASE_AMOUNT;
                if (performance.getAudience() > Constants.COMEDY_AUDIENCE_THRESHOLD) {
                    result += Constants.COMEDY_OVER_BASE_CAPACITY_AMOUNT
                            + (Constants.COMEDY_OVER_BASE_CAPACITY_PER_PERSON
                            * (performance.getAudience() - Constants.COMEDY_AUDIENCE_THRESHOLD));
                }
                result += Constants.COMEDY_AMOUNT_PER_AUDIENCE * performance.getAudience();
                break;
            case "history":
                result = Constants.HISTORY_BASE_AMOUNT;
                if (performance.getAudience() > Constants.HISTORY_AUDIENCE_THRESHOLD) {
                    result += Constants.HISTORY_OVER_BASE_CAPACITY_PER_PERSON
                            * (performance.getAudience() - Constants.HISTORY_AUDIENCE_THRESHOLD);
                }
                break;
            case "pastoral":
                result = Constants.PASTORAL_BASE_AMOUNT;
                if (performance.getAudience() > Constants.PASTORAL_AUDIENCE_THRESHOLD) {
                    result += Constants.PASTORAL_OVER_BASE_CAPACITY_PER_PERSON
                            * (performance.getAudience() - Constants.PASTORAL_AUDIENCE_THRESHOLD);
                }
                break;
            default:
                throw new RuntimeException(String.format("unknown type: %s", play.getType()));
        }
        return result;
    }

    protected int getVolumeCredits(Performance performance) {
        int result = 0;
        Play play = getPlay(performance);
        result += Math.max(performance.getAudience() - Constants.BASE_VOLUME_CREDIT_THRESHOLD, 0);
        if ("comedy".equals(play.getType())) {
            result += performance.getAudience() / Constants.COMEDY_EXTRA_VOLUME_FACTOR;
        }
        return result;
    }

    protected static String usd(int amount) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(amount / Constants.PERCENT_FACTOR);
    }

    protected int getTotalAmount() {
        int result = 0;
        for (Performance p : invoice.getPerformances()) {
            result += getAmount(p);
        }
        return result;
    }

    protected int getTotalVolumeCredits() {
        int result = 0;
        for (Performance p : invoice.getPerformances()) {
            result += getVolumeCredits(p);
        }
        return result;
    }
}
