package com.stanlong.leetcode;

import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 统计射击比赛成绩
 */
public class LeetCode {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        int count = Integer.parseInt(scanner.nextLine());
        int[][] matrix = new int[2][13];
        for(int i=0; i< matrix.length; i++){
            String str = scanner.nextLine();
            for(int j=0; j<count; j++){
                matrix[i][j] = Integer.parseInt(str.split(",")[j]);
            }
        }
        for(int i=0; i< matrix.length; i++){
            System.out.println(Arrays.toString(matrix[i]));
        }
    }
}
