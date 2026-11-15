package week10;

/**
 * Problem 9: Polynomial Calculator
 *
 * TASK:
 * A maths tool stores each polynomial as a singly linked list of terms (coefficient, exponent)
 * in decreasing order of exponent. Two polynomials must be added into a new list in the same order;
 * terms whose coefficients add up to 0 must not appear.
 *
 * Implement Node addPolynomials(Node p, Node q) and print the result in standard form.
 *
 * CONSTRAINTS:
 * - 0 <= number of terms in each polynomial <= 1000
 * - Exponents are distinct within one polynomial and are non-negative
 * - Each input list is visited once
 *
 * COMPLEXITY:
 * - Time Complexity: O(m + n), where m and n are the number of terms in polynomials p and q.
 *   In each comparison step, we process at least one term from p or q, visiting each node exactly once.
 * - Auxiliary Space Complexity: O(m + n) in the worst case to allocate the new nodes for the sum list.
 *   Excluding the output list, it uses O(1) auxiliary space.
 */
public class Problem9_PolynomialCalculator {

    public static class Node {
        public int coeff;
        public int exp;
        public Node next;

        public Node(int coeff, int exp) {
            this.coeff = coeff;
            this.exp = exp;
            this.next = null;
        }

        public Node(int coeff, int exp, Node next) {
            this.coeff = coeff;
            this.exp = exp;
            this.next = next;
        }
    }

    /**
     * Adds two polynomials represented as singly linked lists of terms sorted by descending exponent.
     * Terms with a resulting coefficient of 0 are omitted.
     *
     * @param p Head of first polynomial list.
     * @param q Head of second polynomial list.
     * @return Head of the resulting polynomial list in standard decreasing exponent order.
     */
    public static Node addPolynomials(Node p, Node q) {
        Node dummy = new Node(0, 0);
        Node tail = dummy;

        while (p != null && q != null) {
            if (p.exp > q.exp) {
                // p's exponent is strictly larger, attach copy of p's term
                if (p.coeff != 0) {
                    tail.next = new Node(p.coeff, p.exp);
                    tail = tail.next;
                }
                p = p.next;
            } else if (q.exp > p.exp) {
                // q's exponent is strictly larger, attach copy of q's term
                if (q.coeff != 0) {
                    tail.next = new Node(q.coeff, q.exp);
                    tail = tail.next;
                }
                q = q.next;
            } else {
                // Exponents are equal, add coefficients
                int sumCoeff = p.coeff + q.coeff;
                if (sumCoeff != 0) {
                    tail.next = new Node(sumCoeff, p.exp);
                    tail = tail.next;
                }
                p = p.next;
                q = q.next;
            }
        }

        // Append remaining terms from p
        while (p != null) {
            if (p.coeff != 0) {
                tail.next = new Node(p.coeff, p.exp);
                tail = tail.next;
            }
            p = p.next;
        }

        // Append remaining terms from q
        while (q != null) {
            if (q.coeff != 0) {
                tail.next = new Node(q.coeff, q.exp);
                tail = tail.next;
            }
            q = q.next;
        }

        return dummy.next;
    }

    /**
     * Formats the polynomial list into a clean standard algebraic string representation.
     * E.g., "5x^3 + 9x^2 - 3x + 3" or "4x + 1".
     *
     * @param head Head of the polynomial list.
     * @return Formatted standard form string.
     */
    public static String toStandardForm(Node head) {
        if (head == null) return "0";

        StringBuilder sb = new StringBuilder();
        Node curr = head;
        boolean isFirst = true;

        while (curr != null) {
            int c = curr.coeff;
            int e = curr.exp;

            if (c != 0) {
                if (isFirst) {
                    if (c < 0) {
                        sb.append("-");
                    }
                    int absC = Math.abs(c);
                    appendTerm(sb, absC, e);
                    isFirst = false;
                } else {
                    if (c < 0) {
                        sb.append(" - ");
                    } else {
                        sb.append(" + ");
                    }
                    int absC = Math.abs(c);
                    appendTerm(sb, absC, e);
                }
            }
            curr = curr.next;
        }

        return sb.length() == 0 ? "0" : sb.toString();
    }

    private static void appendTerm(StringBuilder sb, int absC, int e) {
        if (e == 0) {
            sb.append(absC);
        } else if (e == 1) {
            if (absC == 1) {
                sb.append("x");
            } else {
                sb.append(absC).append("x");
            }
        } else {
            if (absC == 1) {
                sb.append("x^").append(e);
            } else {
                sb.append(absC).append("x^").append(e);
            }
        }
    }

    public static void printPolynomial(Node head) {
        System.out.println(toStandardForm(head));
    }

    // Helper method to create a polynomial from arrays of (coefficient, exponent) pairs
    public static Node buildPoly(int[][] terms) {
        Node dummy = new Node(0, 0);
        Node tail = dummy;
        for (int[] term : terms) {
            tail.next = new Node(term[0], term[1]);
            tail = tail.next;
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        System.out.println("--- Problem 9: Polynomial Calculator ---");

        // Sample 1:
        // p = 5x^3 + 4x^2 + 2
        // q = 5x^2 - 3x + 1
        // Expected Output: 5x^3 + 9x^2 - 3x + 3
        Node p1 = buildPoly(new int[][]{{5, 3}, {4, 2}, {2, 0}});
        Node q1 = buildPoly(new int[][]{{5, 2}, {-3, 1}, {1, 0}});
        System.out.println("Sample 1:");
        System.out.print("  p = ");
        printPolynomial(p1);
        System.out.print("  q = ");
        printPolynomial(q1);
        Node sum1 = addPolynomials(p1, q1);
        System.out.print("  p + q = ");
        printPolynomial(sum1);
        System.out.println("  Expected: 5x^3 + 9x^2 - 3x + 3");

        System.out.println();

        // Sample 2:
        // p = 3x^2 + 1
        // q = -3x^2 + 4x
        // Expected Output: 4x + 1
        Node p2 = buildPoly(new int[][]{{3, 2}, {1, 0}});
        Node q2 = buildPoly(new int[][]{{-3, 2}, {4, 1}});
        System.out.println("Sample 2:");
        System.out.print("  p = ");
        printPolynomial(p2);
        System.out.print("  q = ");
        printPolynomial(q2);
        Node sum2 = addPolynomials(p2, q2);
        System.out.print("  p + q = ");
        printPolynomial(sum2);
        System.out.println("  Expected: 4x + 1");

        System.out.println();

        // Sample 3: Complete cancellation
        // p = 2x^3 - 4x + 7
        // q = -2x^3 + 4x - 7
        // Expected Output: 0
        Node p3 = buildPoly(new int[][]{{2, 3}, {-4, 1}, {7, 0}});
        Node q3 = buildPoly(new int[][]{{-2, 3}, {4, 1}, {-7, 0}});
        System.out.println("Sample 3 (Complete Cancellation):");
        System.out.print("  p = ");
        printPolynomial(p3);
        System.out.print("  q = ");
        printPolynomial(q3);
        Node sum3 = addPolynomials(p3, q3);
        System.out.print("  p + q = ");
        printPolynomial(sum3);
        System.out.println("  Expected: 0");
    }
}
