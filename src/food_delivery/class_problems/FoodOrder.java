package food_delivery.class_problems;

public class FoodOrder {
    private String studentName;
    private String dishName;
    private boolean delivered;

    public FoodOrder(String studentName, String dishName) {
        if (studentName == null || studentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be blank");
        }
        if (dishName == null || dishName.trim().isEmpty()) {
            throw new IllegalArgumentException("Dish name cannot be blank");
        }
        this.studentName = studentName.trim();
        this.dishName = dishName.trim();
        this.delivered = false;
    }

    public void markDelivered() {
        if (this.delivered) {
            System.out.println("Warning: Order for " + studentName + " was already marked delivered!");
        } else {
            this.delivered = true;
            System.out.println("Order marked delivered.");
        }
    }

    public static void processBatch(String[][] rawOrders) {
        if (rawOrders == null) return;
        int valid = 0;
        int rejected = 0;
        for (String[] attempt : rawOrders) {
            if (attempt != null && attempt.length == 2) {
                try {
                    new FoodOrder(attempt[0], attempt[1]);
                    valid++;
                } catch (IllegalArgumentException e) {
                    rejected++;
                }
            } else {
                rejected++;
            }
        }
        System.out.println("Valid: " + valid + " | Rejected: " + rejected);
    }
}
