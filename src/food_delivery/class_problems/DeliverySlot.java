package food_delivery.class_problems;

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
