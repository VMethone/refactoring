package theater;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Data transfer object used to separate calculation from rendering.
 */
public class StatementData {

    private final String customer;
    private final List<PerformanceData> performances = new ArrayList<>();

    public StatementData(Invoice invoice, Map<String, Play> plays) {
        /**
         * Construct a StatementData object by computing amounts and credits
         * for each performance in the given invoice.
         */
        this.customer = invoice.getCustomer();
        for (Performance p : invoice.getPerformances()) {
            final Play play = plays.get(p.getPlayID());
            final AbstractPerformanceCalculator calculator =
                    AbstractPerformanceCalculator.createPerformanceCalculator(p, play);
                final int amount = calculator.amountFor();
                final int volumeCredits = calculator.volumeCredits();
                performances.add(
                    new PerformanceData(
                        play.getName(),
                        play.getType(),
                        p.getAudience(),
                        amount,
                        volumeCredits));
        }
    }

    /**
     * Returns the customer for this statement data.
     * @return customer name
     */

    public String getCustomer() {
        return customer;
    }

    public List<PerformanceData> getPerformances() {
        return performances;
    }

    /**
     * Compute the total amount for all performances in cents.
     * @return total amount in cents
     */
    public int totalAmount() {
        int result = 0;
        for (final PerformanceData pd : performances) {
            result += pd.getAmount();
        }
        return result;
    }

    public int volumeCredits() {
        int result = 0;
        for (final PerformanceData pd : performances) {
            result += pd.getVolumeCredits();
        }
        return result;
    }
}
