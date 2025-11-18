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

    /**
     * Returns the amount (in cents) for this performance.
     * @return amount in cents
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Returns the computed volume credits for this performance.
     * @return volume credits
     */
    public int getVolumeCredits() {
        return volumeCredits;
    }
}
