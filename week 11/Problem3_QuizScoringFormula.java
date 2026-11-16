package week11;

/**
 * Problem 3: The Quiz Scoring Formula
 *
 * TASK:
 * The quiz portal lets faculty type a scoring formula such as 2 * (3 + 4) - 10 / 5.
 * Implement int evaluate(String expr) that returns the value of the formula.
 *
 * It may contain:
 * - non-negative integers (possibly multi-digit)
 * - operators: + - * /
 * - round brackets: ( )
 * - spaces
 *
 * RULES:
 * - * and / bind tighter than + and -
 * - Operators of equal precedence are evaluated left to right
 * - / is integer division
 * - Must use an operand stack and an operator stack in a single left-to-right scan
 * - Do NOT convert the whole expression to postfix first
 *
 * COMPLEXITY:
 * - Time Complexity: O(n), where n is the length of expr. Each character is scanned once;
 *   each operand and operator is pushed and popped from the stacks a constant number of times.
 * - Space Complexity: O(n) for the operand and operator stacks.
 */
public class Problem3_QuizScoringFormula {

    /**
     * Evaluates an arithmetic expression in a single pass using two stacks.
     *
     * @param expr The mathematical expression string.
     * @return Integer result of the evaluation.
     */
    public static int evaluate(String expr) {
        if (expr == null || expr.trim().isEmpty()) {
            return 0;
        }

        int n = expr.length();

        // Custom stack for operands (integers)
        int[] valStack = new int[n];
        int valTop = -1;

        // Custom stack for operators (characters)
        char[] opStack = new char[n];
        int opTop = -1;

        for (int i = 0; i < n; i++) {
            char ch = expr.charAt(i);

            // Skip spaces
            if (ch == ' ') {
                continue;
            }

            // If digit, parse complete multi-digit integer
            if (Character.isDigit(ch)) {
                int num = 0;
                while (i < n && Character.isDigit(expr.charAt(i))) {
                    num = num * 10 + (expr.charAt(i) - '0');
                    i++;
                }
                i--; // Step back because outer loop increments i
                valStack[++valTop] = num;
            } else if (ch == '(') {
                // Opening bracket: push to operator stack
                opStack[++opTop] = '(';
            } else if (ch == ')') {
                // Closing bracket: resolve everything back to matching '('
                while (opTop >= 0 && opStack[opTop] != '(') {
                    char op = opStack[opTop--];
                    int b = valStack[valTop--];
                    int a = valStack[valTop--];
                    valStack[++valTop] = applyOp(op, a, b);
                }
                if (opTop >= 0 && opStack[opTop] == '(') {
                    opTop--; // Pop the '('
                }
            } else if (isOperator(ch)) {
                // Operator encountered: process higher or equal precedence operators
                while (opTop >= 0 && opStack[opTop] != '(' && precedence(opStack[opTop]) >= precedence(ch)) {
                    char op = opStack[opTop--];
                    int b = valStack[valTop--];
                    int a = valStack[valTop--];
                    valStack[++valTop] = applyOp(op, a, b);
                }
                opStack[++opTop] = ch;
            }
        }

        // Apply remaining operators
        while (opTop >= 0) {
            char op = opStack[opTop--];
            int b = valStack[valTop--];
            int a = valStack[valTop--];
            valStack[++valTop] = applyOp(op, a, b);
        }

        return valTop >= 0 ? valStack[valTop] : 0;
    }

    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    private static int precedence(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        return 0; // '(' or invalid
    }

    private static int applyOp(char op, int a, int b) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("Division by zero");
                return a / b;
            default: return 0;
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Problem 3: The Quiz Scoring Formula ---");

        // Sample 1:
        // expr = "2 * (3 + 4) - 10 / 5"
        // Expected: 12
        String expr1 = "2 * (3 + 4) - 10 / 5";
        int res1 = evaluate(expr1);
        System.out.println("Sample 1 Input:    \"" + expr1 + "\"");
        System.out.println("Sample 1 Output:   " + res1);
        System.out.println("Expected Output:   12");

        System.out.println();

        // Sample 2:
        // expr = "(8 - 2) * (1 + 2) / 4"
        // Expected: 4
        String expr2 = "(8 - 2) * (1 + 2) / 4";
        int res2 = evaluate(expr2);
        System.out.println("Sample 2 Input:    \"" + expr2 + "\"");
        System.out.println("Sample 2 Output:   " + res2);
        System.out.println("Expected Output:   4");

        System.out.println();

        // Sample 3: Multi-digit and precedence check
        String expr3 = "100 + 20 * 5 - 50 / 2";
        // 100 + 100 - 25 = 175
        int res3 = evaluate(expr3);
        System.out.println("Sample 3 Input:    \"" + expr3 + "\"");
        System.out.println("Sample 3 Output:   " + res3);
        System.out.println("Expected Output:   175");
    }
}
