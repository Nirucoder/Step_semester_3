import os

base_dir = r"C:\Users\user\Desktop\STEP\Step_semester_3\src\bus_booking\class_problems"

files = {
    'BusTicket.java': '''package bus_booking.class_problems;

import java.util.HashSet;
import java.util.Set;

public class BusTicket {
    private String passengerName;
    private String destination;
    private boolean checkedIn;

    public BusTicket(String passengerName, String destination) {
        if (passengerName == null || passengerName.trim().isEmpty() || passengerName.matches(".*\\\\d.*")) {
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
''',
    'FareSplitter.java': '''package bus_booking.class_problems;

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
''',
    'BusRoute.java': '''package bus_booking.class_problems;

public class BusRoute implements Comparable<BusRoute> {
    private String routeCode;
    private String routeName;
    private int priority;

    public BusRoute(String routeCode, String routeName, int priority) {
        this.routeCode = routeCode;
        this.routeName = routeName;
        this.priority = priority;
    }

    public BusRoute(String routeCode, String routeName) {
        this(routeCode, routeName, 3);
    }

    @Override
    public int compareTo(BusRoute other) {
        if (other == null) return 1;
        
        // 1. Compare by priority (higher priority first... wait, usually lower number is higher priority or vice-versa)
        // Let's assume lower priority number = higher priority? Or higher number = higher priority?
        // Let's compare normally: this.priority - other.priority
        if (this.priority != other.priority) {
            return Integer.compare(this.priority, other.priority);
        }
        
        // 2. Tie break by routeCode (case insensitive)
        return this.routeCode.compareToIgnoreCase(other.routeCode);
    }

    public static BusRoute[] rankRoutes(BusRoute[] routes) {
        if (routes == null) return new BusRoute[0];
        
        BusRoute[] ranked = new BusRoute[routes.length];
        System.arraycopy(routes, 0, ranked, 0, routes.length);
        
        for (int i = 0; i < ranked.length - 1; i++) {
            for (int j = 0; j < ranked.length - 1 - i; j++) {
                if (ranked[j].compareTo(ranked[j + 1]) > 0) {
                    BusRoute temp = ranked[j];
                    ranked[j] = ranked[j + 1];
                    ranked[j + 1] = temp;
                }
            }
        }
        return ranked;
    }
}
''',
    'BoardingPenaltyCalculator.java': '''package bus_booking.class_problems;

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
''',
    'BusTicketAccount.java': '''package bus_booking.class_problems;

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
''',
    'SleeperCoachAccount.java': '''package bus_booking.class_problems;

public class SleeperCoachAccount extends BusTicketAccount {
    public SleeperCoachAccount(String bookingId, double ticketFare) {
        super(bookingId, ticketFare);
    }

    public SleeperCoachAccount(String bookingId) {
        super(bookingId);
    }
}
'''
}

for filename, content in files.items():
    with open(os.path.join(base_dir, filename), 'w') as f:
        f.write(content)

print('Session 3 files created.')
