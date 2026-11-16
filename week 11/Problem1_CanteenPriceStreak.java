package week11;

import java.util.Arrays;

/**
 * Problem 1: The Canteen Price Streak
 *
 * TASK:
 * The canteen manager records the price of a samosa plate every day. For each day, she wants
 * the price streak: the number of consecutive days ending today (including today) on which the price
 * was less than or equal to today's price.
 *
 * Implement int[] priceStreak(int[] price) that returns the streak for every day.
 * A nested-loop answer is O(n^2); your solution must run in O(n) by keeping on a stack only the
 * earlier days that can still block a future streak.
 *
 * COMPLEXITY:
 * - Time Complexity: O(n), where n is the number of days. Each index is pushed onto the stack
 *   exactly once and popped at most once.
 * - Space Complexity: O(n) to store the monotonic stack and the returned streak array.
 */
public class Problem1_CanteenPriceStreak {

    /**
     * Calculates the price streak for each day in O(n) time using a monotonic decreasing stack.
     *
     * @param price Array of daily prices.
     * @return Array of streaks for each day.
     */
    public static int[] priceStreak(int[] price) {
        if (price == null || price.length == 0) {
            return new int[0];
        }

        int n = price.length;
        int[] streaks = new int[n];

        // Custom array-based stack storing indices of days
        int[] stack = new int[n];
        int top = -1;

        for (int i = 0; i < n; i++) {
            // Pop all earlier days whose price is <= today's price
            while (top >= 0 && price[stack[top]] <= price[i]) {
                top--;
            }

            // If stack is empty, today's price is >= all previous prices
            // Otherwise, distance to the last blocking day that had a strictly higher price
            streaks[i] = (top == -1) ? (i + 1) : (i - stack[top]);

            // Push today's index onto the stack
            stack[++top] = i;
        }

        return streaks;
    }

    public static void main(String[] args) {
        System.out.println("--- Problem 1: The Canteen Price Streak ---");

        // Sample 1:
        // price = [100, 80, 60, 70, 60, 75, 85]
        // Expected: [1, 1, 1, 2, 1, 4, 6]
        int[] price1 = {100, 80, 60, 70, 60, 75, 85};
        int[] result1 = priceStreak(price1);
        System.out.println("Sample 1 Input:    " + Arrays.toString(price1));
        System.out.println("Sample 1 Output:   " + Arrays.toString(result1));
        System.out.println("Expected Output:   [1, 1, 1, 2, 1, 4, 6]");

        System.out.println();

        // Sample 2:
        // price = [10, 20, 30]
        // Expected: [1, 2, 3]
        int[] price2 = {10, 20, 30};
        int[] result2 = priceStreak(price2);
        System.out.println("Sample 2 Input:    " + Arrays.toString(price2));
        System.out.println("Sample 2 Output:   " + Arrays.toString(result2));
        System.out.println("Expected Output:   [1, 2, 3]");
    }
}
