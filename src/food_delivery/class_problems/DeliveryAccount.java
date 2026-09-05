package food_delivery.class_problems;

public class DeliveryAccount {
    private String studentId;
    private double orderValue;

    static {
        // Class-level setup block
    }

    public DeliveryAccount(String studentId, double orderValue) {
        this.studentId = studentId;
        this.orderValue = orderValue;
    }

    public DeliveryAccount(String studentId) {
        this(studentId, 0.0);
    }
    
    public double getOrderValue() {
        return orderValue;
    }

    public final double calculateSurgeFee(int delayMinutes) {
        SurgeFeeCalculator calc = new SurgeFeeCalculator(1.0); // Problem says reuse or simpler flat-rate. We reuse SurgeFeeCalculator.
        return calc.calculateSurgeFee(this.orderValue, delayMinutes);
    }

    public static void processBatch(DeliveryAccount[] accounts, double[] amounts, int[] delayMinutesArray) {
        if (accounts == null || amounts == null || delayMinutesArray == null) return;
        
        int processed = 0;
        int nullSkipped = 0;
        int premium = 0;
        int regular = 0;
        double totalSurge = 0;
        
        int length = Math.min(accounts.length, Math.min(amounts.length, delayMinutesArray.length));
        
        for (int i = 0; i < length; i++) {
            if (accounts[i] == null) {
                nullSkipped++;
            } else {
                processed++;
                if (accounts[i] instanceof PremiumDeliveryAccount) {
                    premium++;
                } else {
                    regular++;
                }
                accounts[i].orderValue = amounts[i];
                double fee = accounts[i].calculateSurgeFee(delayMinutesArray[i]);
                totalSurge += fee;
            }
        }
        
        System.out.println(processed + " processed | " + nullSkipped + " null skipped | " + premium + " premium | " + regular + " regular | grand total surge fees = " + totalSurge);
    }
}
