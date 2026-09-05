package bus_booking.class_problems;

public class FareSplitter {
    private String tripId;
    private double totalFare;
    private int passengerCount;

    public FareSplitter(String tripId, double totalFare, int passengerCount) {
        if (totalFare < 0) throw new IllegalArgumentException("Fare cannot be negative");
        if (passengerCount <= 0) throw new IllegalArgumentException("Passenger count must be positive");
        this.tripId = tripId;
        this.totalFare = totalFare;
        this.passengerCount = passengerCount;
    }

    public FareSplitter(String tripId, double totalFare) {
        this(tripId, totalFare, 1);
    }

    public FareSplitter(String tripId) {
        this(tripId, 0.0, 1);
    }

    public double[] fareBreakdown() {
        if (totalFare == 0.0) {
            double[] res = new double[passengerCount];
            for (int i=0; i<passengerCount; i++) res[i] = 0.0;
            return res;
        }
        long totalPaise = Math.round(totalFare * 100);
        long baseSharePaise = totalPaise / passengerCount;
        long remainder = totalPaise % passengerCount;
        
        double[] res = new double[passengerCount];
        for (int i = 0; i < passengerCount; i++) {
            long share = baseSharePaise;
            // Distribute remainder paise (usually to the last elements to keep it deterministic)
            // The problem says "notice which share absorbs the extra paisa, and why it isn't the first one."
            // Standard convention: Add remainder to the last elements.
            if (i >= passengerCount - remainder) {
                share += 1;
            }
            res[i] = share / 100.0;
        }
        return res;
    }

    public boolean isConfirmationOverdue(int confirmed, int expected) {
        return confirmed < expected;
    }
}
