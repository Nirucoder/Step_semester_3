package week11;

/**
 * Problem 2: The Hackathon Banner Printer
 *
 * TASK:
 * The hackathon banner printer accepts a compressed design code. A pattern k[text] means
 * "print text k times", and patterns can be nested inside each other, like 2[x3[y]].
 * Letters outside any brackets are printed as they are.
 *
 * Implement String expandBanner(String code) that returns the fully expanded text.
 * k is a positive integer that may have more than one digit, and the code is always well formed.
 * Do not use recursion: simulate the nesting with stacks.
 *
 * CONSTRAINTS:
 * - Iterative solution only (no recursion)
 * - k may be multi-digit
 *
 * COMPLEXITY:
 * - Time Complexity: O(L), where L is the total length of the expanded string.
 *   Each character is parsed once, and strings are concatenated proportionally to their repetition.
 * - Space Complexity: O(L) to store the intermediate builders on the stack and the expanded output.
 */
public class Problem2_HackathonBannerPrinter {

    /**
     * Expands a compressed banner code using two stacks (one for multipliers, one for strings).
     *
     * @param code Compressed input string.
     * @return Fully expanded string.
     */
    public static String expandBanner(String code) {
        if (code == null || code.isEmpty()) {
            return "";
        }

        // Custom stack for counts
        int[] countStack = new int[code.length()];
        int countTop = -1;

        // Custom stack for string builders
        StringBuilder[] stringStack = new StringBuilder[code.length()];
        int stringTop = -1;

        StringBuilder currentText = new StringBuilder();
        int currentK = 0;

        for (int i = 0; i < code.length(); i++) {
            char ch = code.charAt(i);

            if (Character.isDigit(ch)) {
                // Multi-digit repeat count accumulation
                currentK = currentK * 10 + (ch - '0');
            } else if (ch == '[') {
                // Push repeat count and the text built before this bracket
                countStack[++countTop] = currentK;
                stringStack[++stringTop] = currentText;

                // Reset for the new bracketed scope
                currentK = 0;
                currentText = new StringBuilder();
            } else if (ch == ']') {
                // Pop the repeat count and previous string context
                int repeatCount = countStack[countTop--];
                StringBuilder prevText = stringStack[stringTop--];

                // Append the current segment repeatCount times to prevText
                for (int r = 0; r < repeatCount; r++) {
                    prevText.append(currentText);
                }
                currentText = prevText;
            } else {
                // Regular letter
                currentText.append(ch);
            }
        }

        return currentText.toString();
    }

    public static void main(String[] args) {
        System.out.println("--- Problem 2: The Hackathon Banner Printer ---");

        // Sample 1:
        // code = "3[ab]2[c]"
        // Expected Output: "abababcc"
        String code1 = "3[ab]2[c]";
        String res1 = expandBanner(code1);
        System.out.println("Sample 1 Input:    " + code1);
        System.out.println("Sample 1 Output:   \"" + res1 + "\"");
        System.out.println("Expected Output:   \"abababcc\"");

        System.out.println();

        // Sample 2:
        // code = "2[x3[y]]z"
        // Expected Output: "xyyyxyyyz"
        String code2 = "2[x3[y]]z";
        String res2 = expandBanner(code2);
        System.out.println("Sample 2 Input:    " + code2);
        System.out.println("Sample 2 Output:   \"" + res2 + "\"");
        System.out.println("Expected Output:   \"xyyyxyyyz\"");

        System.out.println();

        // Sample 3: Multi-digit repeat count
        // code = "10[a]"
        // Expected Output: "aaaaaaaaaa"
        String code3 = "10[a]";
        String res3 = expandBanner(code3);
        System.out.println("Sample 3 Input:    " + code3);
        System.out.println("Sample 3 Output:   \"" + res3 + "\"");
        System.out.println("Expected Output:   \"aaaaaaaaaa\"");
    }
}
