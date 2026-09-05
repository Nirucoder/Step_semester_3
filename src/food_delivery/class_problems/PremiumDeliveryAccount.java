package food_delivery.class_problems;

public class PremiumDeliveryAccount extends DeliveryAccount {
    public PremiumDeliveryAccount(String studentId, double orderValue) {
        super(studentId, orderValue);
    }

    public PremiumDeliveryAccount(String studentId) {
        super(studentId);
    }
    
    // Premium accounts might have specialized surge fee settlement in reality, 
    // but the problem states calculateSurgeFee is final on DeliveryAccount.
    // They are distinguished in processBatch via instanceof.
}
