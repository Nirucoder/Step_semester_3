package bus_booking.class_problems;

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
