package week10;

/**
 * Problem 10: The Palindrome Train Code
 *
 * TASK:
 * Each train has a code stored as a singly linked list of digits. Codes that read the same
 * from both ends are reserved for special trains. The check must use constant extra space,
 * and the list must be left exactly as it was.
 *
 * Implement boolean isPalindrome(Node head) in O(n) time and O(1) extra space, restoring the list
 * before returning.
 *
 * CONSTRAINTS:
 * - 1 <= length of list <= 10^5
 * - O(1) additional space; no array or stack copies
 * - The list must be unchanged after the call
 *
 * COMPLEXITY:
 * - Time Complexity: O(n), where n is the number of nodes.
 *   1. Finding the midpoint with slow and fast pointers takes n/2 steps -> O(n).
 *   2. In-place reversal of the second half takes n/2 steps -> O(n).
 *   3. Element-wise comparison takes n/2 steps -> O(n).
 *   4. Restoring the second half through re-reversal takes n/2 steps -> O(n).
 *   Total time: O(n) linear time.
 * - Auxiliary Space Complexity: O(1).
 *   Only a handful of reference pointers (slow, fast, prev, curr, next, p1, p2) are stored.
 * - Comparison with Array Copying:
 *   Copying node values to an array/stack requires O(n) additional memory, which violates the
 *   strict O(1) auxiliary space constraint and risks memory exhaustion on large lists (10^5 nodes).
 *   The pointer-reversal method operates completely in-place without memory overhead and preserves
 *   data integrity by restoring the list before return.
 */
public class Problem10_PalindromeTrainCode {

    public static class Node {
        public int data;
        public Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    /**
     * Checks if the singly linked list is a palindrome in O(n) time and O(1) space,
     * restoring the list to its original configuration prior to returning.
     *
     * @param head Head of the linked list.
     * @return true if the list forms a palindrome, false otherwise.
     */
    public static boolean isPalindrome(Node head) {
        if (head == null || head.next == null) {
            return true;
        }

        // 1. Locate the middle node (first half end) using slow and fast pointers
        Node slow = head;
        Node fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2. Reverse the second half of the list starting after slow
        Node secondHalfHead = reverseList(slow.next);

        // 3. Compare values of the first half and reversed second half
        Node p1 = head;
        Node p2 = secondHalfHead;
        boolean isPalin = true;

        while (p2 != null) {
            if (p1.data != p2.data) {
                isPalin = false;
                break;
            }
            p1 = p1.next;
            p2 = p2.next;
        }

        // 4. Restore the original list by reversing the second half back
        slow.next = reverseList(secondHalfHead);

        return isPalin;
    }

    /**
     * In-place list reversal helper.
     */
    private static Node reverseList(Node head) {
        Node prev = null;
        Node curr = head;
        while (curr != null) {
            Node nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    // Helper method to build a linked list from an array
    public static Node buildList(int[] values) {
        if (values == null || values.length == 0) return null;
        Node dummy = new Node(0);
        Node curr = dummy;
        for (int v : values) {
            curr.next = new Node(v);
            curr = curr.next;
        }
        return dummy.next;
    }

    // Helper method to print the linked list
    public static void printList(Node head) {
        if (head == null) {
            System.out.println("(empty)");
            return;
        }
        StringBuilder sb = new StringBuilder();
        Node curr = head;
        while (curr != null) {
            sb.append(curr.data);
            if (curr.next != null) {
                sb.append(" -> ");
            }
            curr = curr.next;
        }
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        System.out.println("--- Problem 10: The Palindrome Train Code ---");

        // Sample 1:
        // code = 1 -> 2 -> 3 -> 2 -> 1
        // Expected: true
        Node code1 = buildList(new int[]{1, 2, 3, 2, 1});
        System.out.print("Sample 1 Input:       ");
        printList(code1);
        boolean res1 = isPalindrome(code1);
        System.out.println("isPalindrome result:  " + res1 + " (Expected: true)");
        System.out.print("List after check:     ");
        printList(code1);

        System.out.println();

        // Sample 2:
        // code = 1 -> 2 -> 3 -> 2
        // Expected: false
        Node code2 = buildList(new int[]{1, 2, 3, 2});
        System.out.print("Sample 2 Input:       ");
        printList(code2);
        boolean res2 = isPalindrome(code2);
        System.out.println("isPalindrome result:  " + res2 + " (Expected: false)");
        System.out.print("List after check:     ");
        printList(code2);

        System.out.println();

        // Sample 3:
        // code = 1 -> 2 -> 2 -> 1
        // Expected: true (list afterwards: 1 -> 2 -> 2 -> 1)
        Node code3 = buildList(new int[]{1, 2, 2, 1});
        System.out.print("Sample 3 Input:       ");
        printList(code3);
        boolean res3 = isPalindrome(code3);
        System.out.println("isPalindrome result:  " + res3 + " (Expected: true)");
        System.out.print("List after check:     ");
        printList(code3);
    }
}
