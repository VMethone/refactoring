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
        this.customer = invoice.getCustomer();
        for (Performance p : invoice.getPerformances()) {
            Play play = plays.get(p.getPlayID());
            AbstractPerformanceCalculator calculator = AbstractPerformanceCalculator.createPerformanceCalculator(p, play);
            int amount = calculator.amountFor();
            int volumeCredits = calculator.volumeCredits();
            performances.add(new PerformanceData(play.getName(), play.getType(), p.getAudience(), amount, volumeCredits));
        }
    }

    public String getCustomer() {
        return customer;
    }

    public List<PerformanceData> getPerformances() {
        return performances;
    }

    public int totalAmount() {
        int result = 0;
        for (PerformanceData pd : performances) {
            result += pd.getAmount();
        }
        return result;
    }

    public int volumeCredits() {
        int result = 0;
        for (PerformanceData pd : performances) {
            result += pd.getVolumeCredits();
        }
        return result;
    }
}
