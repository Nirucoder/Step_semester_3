package week10;

/**
 * Problem 6: Campus Portal Browser History
 *
 * TASK:
 * A campus portal browser opens on a home page and supports three actions:
 * - visit a new page (which clears all forward history)
 * - go back up to x pages (stops at the first page available)
 * - go forward up to x pages (stops at the last page available)
 *
 * Design a BrowserHistory class backed by your own doubly linked list, with visit(url),
 * back(steps) and forward(steps); back and forward return the current page.
 *
 * CONSTRAINTS:
 * - 1 <= number of actions <= 5000
 * - 1 <= steps <= 100
 * - Each visit is O(1); back and forward are O(steps)
 *
 * COMPLEXITY:
 * - visit(url): O(1) time. Appends the new node directly after `current`, sets `current.next = newNode`,
 *   and unlinks any prior forward chain in constant time.
 * - back(steps): O(min(steps, distance_to_head)) <= O(steps) time. Traverses backward along `prev` links.
 * - forward(steps): O(min(steps, distance_to_tail)) <= O(steps) time. Traverses forward along `next` links.
 * - Space Complexity: O(N) where N is the total number of retained historical nodes in the active path.
 *
 * DISCARDING FORWARD HISTORY AND GARBAGE COLLECTION:
 * When visit(url) is called, `current.next` is overwritten with the new node reference, and the new node's
 * `prev` is set to `current`. The old forward chain (previously starting at the old `current.next`) loses
 * its root reference from the active browser history. Because Java manages memory via automatic garbage
 * collection, all nodes in the discarded forward chain that are no longer reachable from any live reference
 * become eligible for garbage collection and their memory is reclaimed automatically.
 */
public class Problem6_CampusPortalBrowserHistory {

    public static class Node {
        public String url;
        public Node prev;
        public Node next;

        public Node(String url) {
            this.url = url;
            this.prev = null;
            this.next = null;
        }
    }

    public static class BrowserHistory {
        private Node current;

        /**
         * Initializes the browser with the given homepage.
         *
         * @param homepage The initial URL opened in the browser.
         */
        public BrowserHistory(String homepage) {
            this.current = new Node(homepage);
        }

        /**
         * Visits a new url from the current page. Clears all forward history.
         * Time Complexity: O(1)
         *
         * @param url The URL to visit.
         */
        public void visit(String url) {
            Node newNode = new Node(url);
            // Link current to newNode and drop whatever followed current
            current.next = newNode;
            newNode.prev = current;
            current = newNode;
        }

        /**
         * Moves back in history up to `steps` times.
         * Stops at the very first page if steps exceeds the available back history.
         * Time Complexity: O(steps)
         *
         * @param steps Number of steps to move backward.
         * @return The current page URL after moving back.
         */
        public String back(int steps) {
            while (steps > 0 && current.prev != null) {
                current = current.prev;
                steps--;
            }
            return current.url;
        }

        /**
         * Moves forward in history up to `steps` times.
         * Stops at the latest page if steps exceeds available forward history.
         * Time Complexity: O(steps)
         *
         * @param steps Number of steps to move forward.
         * @return The current page URL after moving forward.
         */
        public String forward(int steps) {
            while (steps > 0 && current.next != null) {
                current = current.next;
                steps--;
            }
            return current.url;
        }

        public String getCurrentUrl() {
            return current != null ? current.url : "";
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Problem 6: Campus Portal Browser History ---");

        // Example from assignment:
        // home = srm.edu
        // visit a.com, visit b.com, visit c.com
        // back 1, back 1, forward 1, visit d.com, forward 2, back 2, back 7
        // Expected Output: b.com, a.com, b.com, d.com, a.com, srm.edu

        BrowserHistory browser = new BrowserHistory("srm.edu");
        System.out.println("Opened home: srm.edu");

        browser.visit("a.com");
        System.out.println("visit a.com");
        browser.visit("b.com");
        System.out.println("visit b.com");
        browser.visit("c.com");
        System.out.println("visit c.com");

        StringBuilder outputs = new StringBuilder();

        String r1 = browser.back(1);
        outputs.append(r1).append(", ");
        System.out.println("back 1    -> " + r1);

        String r2 = browser.back(1);
        outputs.append(r2).append(", ");
        System.out.println("back 1    -> " + r2);

        String r3 = browser.forward(1);
        outputs.append(r3).append(", ");
        System.out.println("forward 1 -> " + r3);

        browser.visit("d.com");
        System.out.println("visit d.com (cleared forward history)");

        String r4 = browser.forward(2);
        outputs.append(r4).append(", ");
        System.out.println("forward 2 -> " + r4);

        String r5 = browser.back(2);
        outputs.append(r5).append(", ");
        System.out.println("back 2    -> " + r5);

        String r6 = browser.back(7);
        outputs.append(r6);
        System.out.println("back 7    -> " + r6);

        System.out.println("\nCombined Results: " + outputs.toString());
        System.out.println("Expected Output:  b.com, a.com, b.com, d.com, a.com, srm.edu");
    }
}
