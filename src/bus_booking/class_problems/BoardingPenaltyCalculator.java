package bus_booking.class_problems;

public final class BoardingPenaltyCalculator {
    private final double minimumPenaltyPercent;

    public BoardingPenaltyCalculator(double minimumPenaltyPercent) {
        this.minimumPenaltyPercent = minimumPenaltyPercent;
    }

    public final double calculatePenalty(double ticketFare, int minutesLate) {
        if (ticketFare < 0 || minutesLate < 0) {
            throw new IllegalArgumentException("Negative value provided");
        }
        if (minutesLate == 0) return 0.0;
        
        double penalty = 0.0;
        if (minutesLate > 15) {
            penalty += (minutesLate - 15) * 0.02 * ticketFare;
            minutesLate = 15;
        }
        if (minutesLate > 5) {
            penalty += (minutesLate - 5) * 0.01 * ticketFare;
            minutesLate = 5;
        }
        if (minutesLate > 0) {
            penalty += minutesLate * 0.005 * ticketFare;
        }
        
        double minPenalty = ticketFare * (minimumPenaltyPercent / 100.0);
        return Math.max(penalty, minPenalty);
    }
}
