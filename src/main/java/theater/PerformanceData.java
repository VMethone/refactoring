package theater;

/**
 * Data object representing the computed details for a performance.
 */
public class PerformanceData {

    private final String name;
    private final String type;
    private final int audience;
    private final int amount;
    private final int volumeCredits;

    public PerformanceData(String name, String type, int audience, int amount, int volumeCredits) {
        this.name = name;
        this.type = type;
        this.audience = audience;
        this.amount = amount;
        this.volumeCredits = volumeCredits;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getAudience() {
        return audience;
    }

    public int amountFor() {
        return amount;
    }

    public int getAmount() {
        return amount;
    }

    public int getVolumeCredits() {
        return volumeCredits;
    }
}
