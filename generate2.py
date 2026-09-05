import os

base_dir = r"C:\Users\user\Desktop\STEP\Step_semester_3\src\food_delivery\class_problems"

files = {
    'FoodOrder.java': '''package food_delivery.class_problems;

public class FoodOrder {
    private String studentName;
    private String dishName;
    private boolean delivered;

    public FoodOrder(String studentName, String dishName) {
        if (studentName == null || studentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be blank");
        }
        if (dishName == null || dishName.trim().isEmpty()) {
            throw new IllegalArgumentException("Dish name cannot be blank");
        }
        this.studentName = studentName.trim();
        this.dishName = dishName.trim();
        this.delivered = false;
    }

    public void markDelivered() {
        if (this.delivered) {
            System.out.println("Warning: Order for " + studentName + " was already marked delivered!");
        } else {
            this.delivered = true;
            System.out.println("Order marked delivered.");
        }
    }

    public static void processBatch(String[][] rawOrders) {
        if (rawOrders == null) return;
        int valid = 0;
        int rejected = 0;
        for (String[] attempt : rawOrders) {
            if (attempt != null && attempt.length == 2) {
                try {
                    new FoodOrder(attempt[0], attempt[1]);
                    valid++;
                } catch (IllegalArgumentException e) {
                    rejected++;
                }
            } else {
                rejected++;
            }
        }
        System.out.println("Valid: " + valid + " | Rejected: " + rejected);
    }
}
''',
    'DeliverySlot.java': '''package food_delivery.class_problems;

public class DeliverySlot {
    private String orderId;
    private String timeSlot;

    public DeliverySlot(String orderId, String timeSlot) {
        this.orderId = orderId;
        this.timeSlot = (timeSlot != null && !timeSlot.trim().isEmpty()) ? timeSlot : "ASAP";
    }

    public DeliverySlot(String orderId) {
        this(orderId, "ASAP");
    }

    public boolean isPeakHour() {
        if ("12:00-13:00".equals(timeSlot) || "13:00-14:00".equals(timeSlot) || 
            "19:00-20:00".equals(timeSlot) || "20:00-21:00".equals(timeSlot)) {
            return true;
        }
        return false;
    }
}
''',
    'Canteen.java': '''package food_delivery.class_problems;

public class Canteen {
    private String canteenCode;
    private String canteenName;
    private int trustScore;

    public Canteen(String canteenCode, String canteenName, int trustScore) {
        this.canteenCode = canteenCode;
        this.canteenName = canteenName;
        this.trustScore = trustScore;
    }

    public Canteen(String canteenCode, String canteenName) {
        this(canteenCode, canteenName, 3);
    }

    public String getCanteenCode() {
        return canteenCode;
    }

    public int compareTo(Canteen other) {
        if (other == null) return 1;
        
        // 1. Compare by trustScore descending
        if (this.trustScore != other.trustScore) {
            return Integer.compare(other.trustScore, this.trustScore);
        }
        
        // 2. Compare by canteenCode length
        if (this.canteenCode.length() != other.canteenCode.length()) {
            return Integer.compare(this.canteenCode.length(), other.canteenCode.length());
        }
        
        // 3. Compare by canteenCode string (case-insensitive)
        return this.canteenCode.compareToIgnoreCase(other.canteenCode);
    }

    public static Canteen[] rankCanteens(Canteen[] canteens) {
        if (canteens == null) return new Canteen[0];
        
        Canteen[] ranked = new Canteen[canteens.length];
        System.arraycopy(canteens, 0, ranked, 0, canteens.length);
        
        for (int i = 0; i < ranked.length - 1; i++) {
            for (int j = 0; j < ranked.length - 1 - i; j++) {
                if (ranked[j].compareTo(ranked[j + 1]) > 0) {
                    Canteen temp = ranked[j];
                    ranked[j] = ranked[j + 1];
                    ranked[j + 1] = temp;
                }
            }
        }
        return ranked;
    }
}
''',
    'SurgeFeeCalculator.java': '''package food_delivery.class_problems;

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
''',
    'DeliveryAccount.java': '''package food_delivery.class_problems;

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
''',
    'PremiumDeliveryAccount.java': '''package food_delivery.class_problems;

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
'''
}

for filename, content in files.items():
    with open(os.path.join(base_dir, filename), 'w') as f:
        f.write(content)

print('Session 2 files created.')
