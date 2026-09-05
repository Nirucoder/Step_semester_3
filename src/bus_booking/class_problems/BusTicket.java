package bus_booking.class_problems;

import java.util.HashSet;
import java.util.Set;

public class BusTicket {
    private String passengerName;
    private String destination;
    private boolean checkedIn;

    public BusTicket(String passengerName, String destination) {
        if (passengerName == null || passengerName.trim().isEmpty() || passengerName.matches(".*\\d.*")) {
            // Assume digits make a name invalid per example {"Ravi123","Pune"} rejected
            throw new IllegalArgumentException("Invalid passenger name");
        }
        if (destination == null || destination.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid destination");
        }
        this.passengerName = passengerName.trim();
        this.destination = destination.trim();
        this.checkedIn = false;
    }

    public void markCheckedIn() {
        if (this.checkedIn) {
            System.out.println("Warning: Passenger " + passengerName + " was already marked checked in!");
        } else {
            this.checkedIn = true;
            System.out.println("Passenger marked checked in.");
        }
    }

    public static void processBatch(String[][] rawBookings) {
        if (rawBookings == null) return;
        
        int valid = 0;
        int rejected = 0;
        int duplicates = 0;
        Set<String> seen = new HashSet<>();
        
        for (String[] attempt : rawBookings) {
            if (attempt != null && attempt.length == 2) {
                try {
                    BusTicket t = new BusTicket(attempt[0], attempt[1]);
                    String key = t.passengerName.toLowerCase() + "|" + t.destination.toLowerCase();
                    if (seen.contains(key)) {
                        duplicates++;
                    } else {
                        seen.add(key);
                        valid++;
                    }
                } catch (IllegalArgumentException e) {
                    rejected++;
                }
            } else {
                rejected++;
            }
        }
        System.out.println("Valid: " + valid + " | Rejected: " + rejected + " | Duplicates skipped: " + duplicates);
    }
}
