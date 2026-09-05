package food_delivery.class_problems;

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
