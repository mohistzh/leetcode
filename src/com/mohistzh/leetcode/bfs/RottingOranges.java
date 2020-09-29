package com.mohistzh.leetcode.bfs;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.Queue;

/**
 * In a given grid, each cell can have one of three values:
 *
 * the value 0 representing an empty cell;
 * the value 1 representing a fresh orange;
 * the value 2 representing a rotten orange.
 * Every minute, any fresh orange that is adjacent (4-directionally) to a rotten orange becomes rotten.
 *
 * Return the minimum number of minutes that must elapse until no cell has a fresh orange.  If this is impossible, return -1 instead.
 *
 *
 *
 * Example 1:
 *
 *
 *
 * Input: [[2,1,1],[1,1,0],[0,1,1]]
 * Output: 4
 *
 * Example 2:
 *
 * Input: [[2,1,1],[0,1,1],[1,0,1]]
 * Output: -1
 * Explanation:  The orange in the bottom left corner (row 2, column 0) is never rotten, because rotting only happens 4-directionally.
 *
 * Example 3:
 *
 * Input: [[0,2]]
 * Output: 0
 * Explanation:  Since there are already no fresh oranges at minute 0, the answer is just 0.
 *
 *
 * Note:
 *
 * 1 <= grid.length <= 10
 * 1 <= grid[0].length <= 10
 * grid[i][j] is only 0, 1, or 2.
 *
 *
 * @Author Z
 * @Date 2020/9/29
 **/
public class RottingOranges {

    /**
     * bfs approach
     * @param grid
     * @return
     */
    public static int orangesRotting(int[][] grid) {
        int[][] directions = {
                {-1, 0}, // up
                {1, 0}, // down
                {0, -1}, // left
                {0, 1} // right
        };
        int empty = 0, fresh = 1, rotten = 2;
        int row = grid.length, col = grid[0].length;
        Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
        /**
         * use a variable to track the target number to check if all fresh oranges are rotten as well
         */
        int freshCounter = 0;
        /**
         * find all rotten oranges as our first level tree nodes
         */
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == rotten) {
                    queue.add(new Pair<>(i, j));
                }
                if (grid[i][j] == fresh) {
                    freshCounter++;
                }
            }
        }
        if (queue.size() == 0 || queue.size() == row * col) {
            return 0;
        }
        int ans = 0;
        while (!queue.isEmpty() && freshCounter > 0) {
            ans++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Pair<Integer, Integer> pair = queue.poll();
                for (int[] direction : directions) {
                    int x = pair.getKey() + direction[0];
                    int y = pair.getValue() + direction[1];
                    if (x < 0 || x >= row || y < 0 || y >= col) {
                        continue;
                    }
                    if (grid[x][y] == empty || grid[x][y] == rotten) {
                        continue;
                    }
                    freshCounter--;
                    grid[x][y] = rotten;
                    queue.add(new Pair<Integer, Integer>(x, y));
                }
            }

        }
        return freshCounter == 0 ? ans : -1;
    }
    public static void main(String[] args) {
        int[][] grid1 = {
                {0, 2}
        };
        int[][] grid2 = {
                {2, 1, 1},
                {1, 1, 0},
                {0, 1, 1}
        };
        int[][] grid3 = {
                {2, 1, 1},
                {0, 1, 1},
                {1, 0, 1}
        };
        System.out.println(orangesRotting(grid1));
        System.out.println(orangesRotting(grid2));
        System.out.println(orangesRotting(grid3));
    }
}
