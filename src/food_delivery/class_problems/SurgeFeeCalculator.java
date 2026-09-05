package food_delivery.class_problems;

public class SurgeFeeCalculator {
    private final double minimumSurgePercent;

    public SurgeFeeCalculator(double minimumSurgePercent) {
        this.minimumSurgePercent = minimumSurgePercent;
    }

    public final double calculateSurgeFee(double orderValue, int delayMinutes) {
        if (orderValue < 0 || delayMinutes < 0) {
            throw new IllegalArgumentException("Negative value provided");
        }
        if (delayMinutes == 0) return 0.0;
        
        double fee = 0.0;
        if (delayMinutes > 15) {
            fee += (delayMinutes - 15) * 0.02 * orderValue;
            delayMinutes = 15;
        }
        if (delayMinutes > 5) {
            fee += (delayMinutes - 5) * 0.01 * orderValue;
            delayMinutes = 5;
        }
        if (delayMinutes > 0) {
            fee += delayMinutes * 0.005 * orderValue;
        }
        
        double minFee = orderValue * (minimumSurgePercent / 100.0);
        return Math.max(fee, minFee);
    }
}
