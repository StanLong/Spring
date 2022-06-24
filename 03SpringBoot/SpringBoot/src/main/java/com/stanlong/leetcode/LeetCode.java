package com.stanlong.leetcode;

import java.io.BufferedInputStream;
import java.util.*;

/**
 * 用户调度问题
 */
public class LeetCode {

    private static int res = Integer.MAX_VALUE;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        int count = scanner.nextInt();
        int[][] matrix = new int[count][3];
        for(int i=0; i< matrix.length; i++){
            for(int j=0; j< matrix[i].length; j++){
                matrix[i][j] = scanner.nextInt();
            }
        }
        LinkedList<Integer> path = new LinkedList<>();
        combine(matrix, 0, -1, path);
        System.out.println(res);
    }

    /**
     * 回溯
     */
    public static void combine(int[][] matrix, int k, int preIndex, LinkedList<Integer> path){
        if(k== matrix.length){
            res = Math.min(res, path.stream().mapToInt(value -> value).sum());
            return;
        }
        for(int i=0; i<matrix[0].length; i++){
            if(i == preIndex){
                continue;
            }
            path.add(matrix[k][i]);
            combine(matrix, k+1, i, path);
            path.removeLast();
        }
    }
}