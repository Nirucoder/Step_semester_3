package bus_booking.class_problems;

public class BusTicketAccount {
    private String bookingId;
    private double ticketFare;

    static {
        // Shared state setup
    }

    public BusTicketAccount(String bookingId, double ticketFare) {
        this.bookingId = bookingId;
        this.ticketFare = ticketFare;
    }

    public BusTicketAccount(String bookingId) {
        this(bookingId, 0.0);
    }
    
    public double getTicketFare() {
        return ticketFare;
    }

    public final double calculatePenalty(int minutesLate) {
        BoardingPenaltyCalculator calc = new BoardingPenaltyCalculator(1.0); 
        return calc.calculatePenalty(this.ticketFare, minutesLate);
    }

    public static void processBatch(BusTicketAccount[] accounts, double[] amounts, int[] minutesLateArray) {
        if (accounts == null || amounts == null || minutesLateArray == null) return;
        
        int processed = 0;
        int nullSkipped = 0;
        int sleeper = 0;
        int regular = 0;
        double totalPenalty = 0;
        
        int length = Math.min(accounts.length, Math.min(amounts.length, minutesLateArray.length));
        
        for (int i = 0; i < length; i++) {
            if (accounts[i] == null) {
                nullSkipped++;
            } else {
                processed++;
                if (accounts[i] instanceof SleeperCoachAccount) {
                    sleeper++;
                } else {
                    regular++;
                }
                accounts[i].ticketFare = amounts[i];
                double penalty = accounts[i].calculatePenalty(minutesLateArray[i]);
                totalPenalty += penalty;
            }
        }
        
        System.out.println(processed + " processed | " + nullSkipped + " null skipped | " + sleeper + " sleeper | " + regular + " regular | grand total penalties = " + totalPenalty);
    }
}
