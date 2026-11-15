package week10;

/**
 * Problem 2: Merging Two Ticket Counters
 *
 * TASK:
 * Two railway counters each produce a singly linked list of ticket numbers in ascending order.
 * At closing time, both lists must be combined into one ascending list.
 *
 * Implement Node mergeSorted(Node a, Node b) that merges the lists by relinking the existing nodes
 * and returns the new head. Duplicates are kept.
 *
 * CONSTRAINTS:
 * - 0 <= length of each list <= 10^5
 * - No new nodes may be created (a single dummy node is allowed)
 *
 * COMPLEXITY:
 * - Time Complexity: O(m + n), where m and n are the lengths of lists a and b.
 *   Each iteration advances at least one pointer by one node, performing constant work.
 * - Auxiliary Space Complexity: O(1). Only a single dummy sentinel node and references
 *   are used; nodes are spliced in-place without copying.
 * - Comparison with Array Sort:
 *   Copying into an array and sorting would take O((m + n) log(m + n)) time and O(m + n) auxiliary space.
 *   In contrast, in-place list merging takes linear O(m + n) time and O(1) space.
 */
public class Problem2_MergingTwoTicketCounters {

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
     * Merges two sorted singly linked lists by relinking existing nodes.
     *
     * @param a Head of the first sorted linked list.
     * @param b Head of the second sorted linked list.
     * @return Head of the merged sorted linked list.
     */
    public static Node mergeSorted(Node a, Node b) {
        // Base edge cases: if either list is empty, return the other
        if (a == null) return b;
        if (b == null) return a;

        // Dummy sentinel node to simplify head attachment
        Node dummy = new Node(0);
        Node tail = dummy;

        // Traverse both lists and splice the smaller node
        while (a != null && b != null) {
            if (a.data <= b.data) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }

        // Attach whatever remaining portion exists
        if (a != null) {
            tail.next = a;
        } else {
            tail.next = b;
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
        System.out.println("--- Problem 2: Merging Two Ticket Counters ---");

        // Sample 1:
        // a = 3 -> 8 -> 15
        // b = 5 -> 8 -> 12 -> 20
        // Expected: 3 -> 5 -> 8 -> 8 -> 12 -> 15 -> 20
        Node a1 = buildList(new int[]{3, 8, 15});
        Node b1 = buildList(new int[]{5, 8, 12, 20});
        System.out.print("Sample 1 List a: ");
        printList(a1);
        System.out.print("Sample 1 List b: ");
        printList(b1);
        Node result1 = mergeSorted(a1, b1);
        System.out.print("Merged Output:   ");
        printList(result1);

        System.out.println();

        // Sample 2:
        // a = (empty), b = 4
        // Expected: 4
        Node a2 = buildList(new int[]{});
        Node b2 = buildList(new int[]{4});
        System.out.print("Sample 2 List a: ");
        printList(a2);
        System.out.print("Sample 2 List b: ");
        printList(b2);
        Node result2 = mergeSorted(a2, b2);
        System.out.print("Merged Output:   ");
        printList(result2);

        System.out.println();

        // Extra Edge Case: both empty
        Node a3 = null;
        Node b3 = null;
        Node result3 = mergeSorted(a3, b3);
        System.out.print("Both Empty Output: ");
        printList(result3);
    }
}
