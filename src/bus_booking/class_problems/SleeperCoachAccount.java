package bus_booking.class_problems;

public class SleeperCoachAccount extends BusTicketAccount {
    public SleeperCoachAccount(String bookingId, double ticketFare) {
        super(bookingId, ticketFare);
    }

    public SleeperCoachAccount(String bookingId) {
        super(bookingId);
    }
}
