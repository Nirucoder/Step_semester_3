package week10;

/**
 * Problem 5: Cancelling the k-th Last Order
 *
 * TASK:
 * A restaurant keeps today's orders in a singly linked list, newest at the end.
 * A customer cancels the order that is k-th from the end.
 * Implement Node removeKthFromEnd(Node head, int k) that removes that node in a single pass
 * and returns the head.
 *
 * CONSTRAINTS:
 * - 1 <= k <= length of list <= 10^4
 * - Single pass only
 *
 * COMPLEXITY:
 * - Time Complexity: O(n), where n is the length of the list.
 *   The lead pointer advances through each node at most once in a single pass.
 * - Auxiliary Space Complexity: O(1).
 *   Only a dummy node and two pointer references (lead, trailing) are used.
 *
 * HOW REMOVING THE HEAD (SAMPLE 2) IS HANDLED WITHOUT A SPECIAL CASE:
 * A dummy sentinel node is initialized such that `dummy.next = head`.
 * The trailing pointer begins at `dummy`.
 * The lead pointer starts k + 1 steps ahead of `trailing` (from dummy, moving k + 1 steps forward).
 * When k equals the length of the list (i.e., removing the head), the lead pointer advances
 * k + 1 steps, immediately reaching null (off the end of the list).
 * Because the lead pointer is already at null, the main while loop does not execute at all,
 * and the trailing pointer remains resting at `dummy`.
 * Then, executing `trailing.next = trailing.next.next` changes `dummy.next` directly to `head.next`,
 * which removes the original head node without needing any `if (k == length)` check or special-case logic.
 * Finally, returning `dummy.next` yields the new head cleanly.
 */
public class Problem5_CancellingKthLastOrder {

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
     * Removes the k-th node from the end of a singly linked list in a single pass.
     *
     * @param head Head of the linked list.
     * @param k 1-based index from the end of the list to remove.
     * @return Head of the modified linked list.
     */
    public static Node removeKthFromEnd(Node head, int k) {
        if (head == null || k <= 0) return head;

        // Create a dummy node that points to head
        Node dummy = new Node(0, head);
        Node lead = dummy;
        Node trailing = dummy;

        // Advance lead pointer k + 1 steps ahead of trailing
        for (int i = 0; i <= k; i++) {
            if (lead == null) {
                // If k is greater than list length, return head unchanged
                return head;
            }
            lead = lead.next;
        }

        // Advance both lead and trailing together until lead falls off the end
        while (lead != null) {
            lead = lead.next;
            trailing = trailing.next;
        }

        // trailing is now pointing to the node right before the k-th last node
        if (trailing.next != null) {
            trailing.next = trailing.next.next;
        }

        return dummy.next;
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
        System.out.println("--- Problem 5: Cancelling the k-th Last Order ---");

        // Sample 1:
        // orders = 10 -> 20 -> 30 -> 40 -> 50, k = 2
        // Expected Output: 10 -> 20 -> 30 -> 50
        Node orders1 = buildList(new int[]{10, 20, 30, 40, 50});
        System.out.print("Sample 1 Input (k = 2): ");
        printList(orders1);
        Node res1 = removeKthFromEnd(orders1, 2);
        System.out.print("Sample 1 Output:        ");
        printList(res1);

        System.out.println();

        // Sample 2:
        // orders = 10 -> 20 -> 30 -> 40 -> 50, k = 5
        // Expected Output: 20 -> 30 -> 40 -> 50
        Node orders2 = buildList(new int[]{10, 20, 30, 40, 50});
        System.out.print("Sample 2 Input (k = 5): ");
        printList(orders2);
        Node res2 = removeKthFromEnd(orders2, 5);
        System.out.print("Sample 2 Output:        ");
        printList(res2);

        System.out.println();

        // Sample 3: Single element list, k = 1
        // Expected Output: (empty)
        Node orders3 = buildList(new int[]{99});
        System.out.print("Single Element (k = 1): ");
        printList(orders3);
        Node res3 = removeKthFromEnd(orders3, 1);
        System.out.print("Result:                 ");
        printList(res3);
    }
}
