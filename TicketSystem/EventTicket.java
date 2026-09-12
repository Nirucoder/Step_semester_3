public class EventTicket {
    protected String attendeeId;
    protected double basePrice;
    protected double balanceDue;
    
    private double[] lateFeeHistory = new double[10];
    private int lateFeeCount = 0;
    
    public final String ticketId;
    private static int ticketsIssued = 0;
    
    public EventTicket(double basePrice) {
        this.basePrice = basePrice;
        this.balanceDue = basePrice;
        ticketsIssued++;
        this.ticketId = "TCK-" + (1000 + ticketsIssued);
    }
    
    public EventTicket(String attendeeId, double basePrice) {
        this(basePrice);
        if (attendeeId == null || attendeeId.trim().isEmpty() || attendeeId.length() < 4) {
            throw new IllegalArgumentException("Invalid attendeeId");
        }
        this.attendeeId = attendeeId;
    }
    
    public void pay(double amount) {
        this.balanceDue -= amount;
    }
    
    public void pay(double amount, String mode) {
        System.out.println("Paid via " + mode);
        pay(amount);
    }
    
    public double getBalanceDue() {
        return this.balanceDue;
    }
    
    public static String registerBatch(String[] attendeeIds, double basePrice) {
        int registered = 0;
        int rejected = 0;
        for (String id : attendeeIds) {
            try {
                new EventTicket(id, basePrice);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        return "Registered: " + registered + " | Rejected: " + rejected;
    }
    
    public static String classifyGeneration(EventTicket ticket) {
        if (ticket instanceof PremiumWorkshopTicket) {
            return "Multilevel descendant (3 generations deep)";
        } else if (ticket instanceof WorkshopTicket) {
            return "Multilevel descendant (2 generations deep)";
        } else if (ticket instanceof HackathonTicket) {
            return "Hierarchical sibling (independent branch)";
        }
        return "Base";
    }
    
    public static double getTotalBalanceDue(EventTicket[] tickets) {
        double total = 0;
        for (EventTicket ticket : tickets) {
            if (ticket != null) {
                total += ticket.getBalanceDue();
            }
        }
        return total;
    }
    
    public String printTicket() {
        return "Standard Event Ticket | Balance Due: " + this.balanceDue;
    }
    
    protected void applyLateFee(double amount) {
        if (amount > 0 && lateFeeCount < lateFeeHistory.length) {
            lateFeeHistory[lateFeeCount++] = amount;
            this.balanceDue += amount;
        }
    }
    
    public double[] getLateFeeHistory() {
        double[] copy = new double[lateFeeCount];
        System.arraycopy(lateFeeHistory, 0, copy, 0, lateFeeCount);
        return copy;
    }
    
    public static String batchPrint(EventTicket[] tickets) {
        StringBuilder sb = new StringBuilder();
        for (EventTicket ticket : tickets) {
            if (ticket == null) continue;
            
            if (ticket instanceof EventTicket && !(ticket instanceof WorkshopTicket) && !(ticket instanceof HackathonTicket)) {
                sb.append("Standard | Balance: ").append(ticket.getBalanceDue()).append(" | ");
            } else {
                sb.append(ticket.printTicket()).append(" ");
                if (ticket instanceof WorkshopTicket) {
                    WorkshopTicket w = (WorkshopTicket) ticket;
                    sb.append("[Track via downcast: ").append(w.getTrack()).append("] | ");
                }
            }
        }
        return sb.toString();
    }
    
    public static boolean isValidPromoCode(String code) {
        if (code == null || code.length() != 5) return false;
        if (code.charAt(0) != 'F') return false;
        if (!Character.isDigit(code.charAt(1))) return false;
        if (!Character.isDigit(code.charAt(2))) return false;
        if (!Character.isDigit(code.charAt(3))) return false;
        if (!Character.isUpperCase(code.charAt(4))) return false;
        return true;
    }
    
    public static int getTicketsIssued() {
        return ticketsIssued;
    }
    
    public static String processNightlySettlement(EventTicket[] tickets) {
        int processed = 0;
        int nullSkipped = 0;
        int group = 0;
        int individual = 0;
        for (EventTicket ticket : tickets) {
            if (ticket == null) {
                nullSkipped++;
            } else {
                processed++;
                if (ticket instanceof GroupTicket) {
                    group++;
                } else {
                    individual++;
                }
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | " + group + " group | " + individual + " individual";
    }
}
