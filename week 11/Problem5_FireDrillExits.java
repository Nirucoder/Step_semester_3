package week11;

import java.util.Arrays;

/**
 * Problem 5: The Fire Drill Exits
 *
 * TASK:
 * For the hostel fire drill, the floor plan is a grid where 'E' is an exit, '#' is a wall
 * and '.' is an open room. Students move up, down, left or right one room per step.
 *
 * Implement int[][] exitDistance(char[][] plan) that returns, for every cell, the minimum
 * number of steps to the nearest exit:
 * - 0 for an exit
 * - -1 for a wall or a room that cannot reach any exit.
 *
 * Running a separate search from every room is too slow; solve it with one breadth-first search
 * that starts from all exits at once (Multi-Source BFS).
 *
 * COMPLEXITY:
 * - Time Complexity: O(R * C), where R and C are the grid dimensions. Each cell is pushed and
 *   popped from the queue at most once.
 * - Space Complexity: O(R * C) to store the result distance matrix and the BFS queue.
 */
public class Problem5_FireDrillExits {

    /**
     * Calculates the shortest step distance from every cell to the nearest exit using Multi-Source BFS.
     *
     * @param plan Grid representing the floor plan ('E': exit, '#': wall, '.': room).
     * @return 2D integer array of distances.
     */
    public static int[][] exitDistance(char[][] plan) {
        if (plan == null || plan.length == 0 || plan[0].length == 0) {
            return new int[0][0];
        }

        int rows = plan.length;
        int cols = plan[0].length;
        int[][] dist = new int[rows][cols];

        // Initialize all distances to -1
        for (int r = 0; r < rows; r++) {
            Arrays.fill(dist[r], -1);
        }

        // Custom array-based ring queue for BFS coordinates (row, col)
        int maxCapacity = rows * cols;
        int[] qRow = new int[maxCapacity];
        int[] qCol = new int[maxCapacity];
        int head = 0;
        int tail = 0;

        // Step 1: Enqueue all exits with distance 0
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (plan[r][c] == 'E') {
                    dist[r][c] = 0;
                    qRow[tail] = r;
                    qCol[tail] = c;
                    tail++;
                }
            }
        }

        // 4-directional movements: up, down, left, right
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        // Step 2: Multi-source BFS
        while (head < tail) {
            int currR = qRow[head];
            int currC = qCol[head];
            head++;

            for (int[] dir : directions) {
                int nextR = currR + dir[0];
                int nextC = currC + dir[1];

                // Check bounds
                if (nextR >= 0 && nextR < rows && nextC >= 0 && nextC < cols) {
                    // Only expand to unvisited open rooms ('.')
                    if (plan[nextR][nextC] == '.' && dist[nextR][nextC] == -1) {
                        dist[nextR][nextC] = dist[currR][currC] + 1;
                        qRow[tail] = nextR;
                        qCol[tail] = nextC;
                        tail++;
                    }
                }
            }
        }

        return dist;
    }

    public static void printGrid(int[][] grid) {
        for (int[] row : grid) {
            for (int val : row) {
                System.out.printf("%3d", val);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Problem 5: The Fire Drill Exits ---");

        // Sample Input:
        // . # . E
        // . . . #
        // E # . .
        char[][] plan = {
            {'.', '#', '.', 'E'},
            {'.', '.', '.', '#'},
            {'E', '#', '.', '.'}
        };

        System.out.println("Floor Plan:");
        for (char[] row : plan) {
            for (char ch : row) {
                System.out.print(ch + " ");
            }
            System.out.println();
        }

        int[][] result = exitDistance(plan);

        System.out.println("\nCalculated Distances:");
        printGrid(result);

        System.out.println("\nExpected Output:");
        System.out.println("  2 -1  1  0");
        System.out.println("  1  2  2 -1");
        System.out.println("  0 -1  3  4");
    }
}
