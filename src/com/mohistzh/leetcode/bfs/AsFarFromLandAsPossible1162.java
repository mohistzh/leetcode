package com.mohistzh.leetcode.bfs;

import java.util.*;

/**
 * Given an N x N grid containing only values 0 and 1, where 0 represents water and 1 represents land, find a water cell such that its distance to the nearest land cell is maximized and return the distance.
 *
 * The distance used in this problem is the Manhattan distance: the distance between two cells (x0, y0) and (x1, y1) is |x0 - x1| + |y0 - y1|.
 *
 * If no land or water exists in the grid, return -1.
 *
 *
 *
 * Example 1:
 *
 *
 *
 * Input: [[1,0,1],[0,0,0],[1,0,1]]
 * Output: 2
 * Explanation:
 * The cell (1, 1) is as far as possible from all the land with distance 2.
 *
 *
 *
 * Example 2:
 *
 *
 *
 * Input: [[1,0,0],[0,0,0],[0,0,0]]
 * Output: 4
 * Explanation:
 * The cell (2, 2) is as far as possible from all the land with distance 4.
 *
 *
 * Note:
 *
 * 1 <= grid.length == grid[0].length <= 100
 * grid[i][j] is 0 or 1
 *
 *
 * @Author Z
 * @Date 2020/9/28
 **/
public class AsFarFromLandAsPossible1162 {


    private static final int[][] directions = {
            {-1, 0}, // up
            {1, 0},  // down
            {0, -1}, //left
            {0, 1}   //right
    };
    /**
     * bfs approach
     * @param grid
     * @return
     */
    public static int maxDistance(int[][] grid) {
        Queue<Coordinate> queue = new LinkedList<>();
        int row = grid.length, col = grid[0].length;
        boolean[][] visited = new boolean[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == 1) {
                    queue.add(new Coordinate(i, j));
                    visited[i][j] = true;
                }
            }
        }

        if (queue.size() == 0 || queue.size() == row * col) {
            return -1;
        }
        int ans = 0;
        while (!queue.isEmpty()) {
            ans++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Coordinate coordinate = queue.poll();
                for (int[] direction : directions) {
                    int x = coordinate.x + direction[0];
                    int y = coordinate.y + direction[1];
                    if (x < 0 || x >= row || y < 0 || y >= row || visited[x][y]) {
                        continue;
                    }
                    visited[x][y] = true;
                    queue.add(new Coordinate(x, y));
                }
            }

        }
        return ans - 1;
    }


    /**
     * brute force approach
     * @param grid
     * @return
     */
    public static int maxDistanceBF(int[][] grid) {
        int maxDistance = -1;
        /**
         * find all lands' coordinates
         */
        List<Coordinate> lands = new ArrayList<>();
        int row = grid.length, col = grid[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == 1) { //lands
                    lands.add(new Coordinate(i, j));
                }
            }
        }
        if (lands.size() == 0) return maxDistance;

        /**
         * find the closest land from each water cell
         */

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == 0) { //water
                    int nearestLandDistance = findNearestLand(lands, new Coordinate(i, j));
                    /**
                     * find the maximum value of the closest land distances among each water cells.
                     */
                    if (nearestLandDistance > maxDistance) {
                        maxDistance = nearestLandDistance;
                    }
                }
            }
        }



        return maxDistance;
    }

    /**
     * find nearest land from the water cell
     * @param lands
     * @param water
     * @return
     */
    private static int findNearestLand(List<Coordinate> lands, Coordinate water) {
        int nearest = Integer.MAX_VALUE;
        for (Coordinate land : lands) {
            int distance = distance(land, water);
            if (distance < nearest) {
                nearest = distance;
            }
        }
        return nearest;
    }

    /**
     * calculate distance between two locations
     * @param p
     * @param q
     * @return
     */
    private static int distance(Coordinate p, Coordinate q) {
        return Math.abs(p.x - q.x) + Math.abs(p.y - q.y);
    }
    public static void main(String[] args) {
        int[][] sample1 = {
                {1,0,1},
                {0,0,0},
                {1,0,1}
        };
        int[][] sample2 = {
                {1,0,0},
                {0,0,0},
                {0,0,0}
        };

        System.out.println(maxDistance(sample1));
        System.out.println(maxDistance(sample2));

    }

}

class Coordinate {
    int x, y;
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
}