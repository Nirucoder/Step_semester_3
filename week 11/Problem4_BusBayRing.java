package week11;

/**
 * Problem 4: The Bus Bay Ring
 *
 * TASK:
 * The campus bus bay has room for a fixed number of buses. Buses can enter or leave from
 * either end of the bay.
 *
 * Design a class BusBay(int capacity) backed by a fixed-size int array used as a ring buffer,
 * with the methods:
 * - insertFront(bus)
 * - insertLast(bus)
 * - deleteFront()
 * - deleteLast()
 * - getFront()
 * - getRear()
 * - isEmpty()
 * - isFull()
 *
 * RULES:
 * - Insert and delete methods return true on success and false if the bay is full or empty.
 * - getFront and getRear return -1 when the bay is empty.
 * - Every method must run in O(1): strictly no shifting of elements and no Java collection classes.
 *
 * COMPLEXITY:
 * - Time Complexity: O(1) for all operations.
 * - Space Complexity: O(C), where C is the capacity of the ring buffer.
 */
public class Problem4_BusBayRing {

    public static class BusBay {
        private final int[] buffer;
        private final int capacity;
        private int front;
        private int size;

        /**
         * Initializes the ring buffer with the given fixed capacity.
         */
        public BusBay(int capacity) {
            this.capacity = capacity;
            this.buffer = new int[capacity];
            this.front = 0;
            this.size = 0;
        }

        /**
         * Inserts a bus at the front of the bay.
         * Runs in O(1) without shifting.
         */
        public boolean insertFront(int bus) {
            if (isFull()) {
                return false;
            }
            // Move front pointer backwards circularly
            front = (front - 1 + capacity) % capacity;
            buffer[front] = bus;
            size++;
            return true;
        }

        /**
         * Inserts a bus at the rear of the bay.
         * Runs in O(1) without shifting.
         */
        public boolean insertLast(int bus) {
            if (isFull()) {
                return false;
            }
            int rear = (front + size) % capacity;
            buffer[rear] = bus;
            size++;
            return true;
        }

        /**
         * Deletes the front bus.
         * Runs in O(1).
         */
        public boolean deleteFront() {
            if (isEmpty()) {
                return false;
            }
            front = (front + 1) % capacity;
            size--;
            return true;
        }

        /**
         * Deletes the rear bus.
         * Runs in O(1).
         */
        public boolean deleteLast() {
            if (isEmpty()) {
                return false;
            }
            size--;
            return true;
        }

        /**
         * Returns the bus at the front, or -1 if the bay is empty.
         */
        public int getFront() {
            if (isEmpty()) {
                return -1;
            }
            return buffer[front];
        }

        /**
         * Returns the bus at the rear, or -1 if the bay is empty.
         */
        public int getRear() {
            if (isEmpty()) {
                return -1;
            }
            int rear = (front + size - 1) % capacity;
            return buffer[rear];
        }

        /**
         * Checks if the bus bay is empty.
         */
        public boolean isEmpty() {
            return size == 0;
        }

        /**
         * Checks if the bus bay is full.
         */
        public boolean isFull() {
            return size == capacity;
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Problem 4: The Bus Bay Ring ---");

        // Example from assignment:
        // BusBay b = new BusBay(3);
        // insertLast(101), insertLast(102), insertFront(100),
        // insertFront(99), getRear(), isFull(), deleteLast(),
        // insertFront(98), getFront()
        // Expected Output: true, true, true, false, 102, true, true, true, 98

        BusBay b = new BusBay(3);

        boolean r1 = b.insertLast(101);
        boolean r2 = b.insertLast(102);
        boolean r3 = b.insertFront(100);
        boolean r4 = b.insertFront(99);
        int r5 = b.getRear();
        boolean r6 = b.isFull();
        boolean r7 = b.deleteLast();
        boolean r8 = b.insertFront(98);
        int r9 = b.getFront();

        String actualOutput = r1 + ", " + r2 + ", " + r3 + ", " + r4 + ", " + r5 + ", " + r6 + ", " + r7 + ", " + r8 + ", " + r9;

        System.out.println("Execution trace:");
        System.out.println("  insertLast(101)  -> " + r1);
        System.out.println("  insertLast(102)  -> " + r2);
        System.out.println("  insertFront(100) -> " + r3);
        System.out.println("  insertFront(99)  -> " + r4);
        System.out.println("  getRear()        -> " + r5);
        System.out.println("  isFull()         -> " + r6);
        System.out.println("  deleteLast()     -> " + r7);
        System.out.println("  insertFront(98)  -> " + r8);
        System.out.println("  getFront()       -> " + r9);

        System.out.println("\nActual Output:   " + actualOutput);
        System.out.println("Expected Output: true, true, true, false, 102, true, true, true, 98");
    }
}
