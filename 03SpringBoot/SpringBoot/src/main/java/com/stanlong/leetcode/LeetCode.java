package com.stanlong.leetcode;

import java.io.BufferedInputStream;
import java.util.*;

/**
 * 比赛
 */
public class LeetCode {

    public static void main(String[] args) {
        Scanner scanner = new Scanner((new BufferedInputStream(System.in)));
        String[] strings = scanner.nextLine().split(",");
        int m = Integer.parseInt(strings[0]);
        int n = Integer.parseInt(strings[1]);
        // 评委或者运动员人数少于3，返回-1
        if(m < 3 || n<3){
            System.out.println(-1);
            scanner.close();
            return;
        }
        int[][] matrix = new int[m][n];
        for(int i=0; i< matrix.length; i++){
            String[] scores = scanner.nextLine().split(",");
            for(int j=0; j< matrix[i].length; j++){
                matrix[i][j] = Integer.parseInt(scores[j]);
            }

        }

        // 第一个评委给第一个选手打分11，无效分数
        if(matrix[0][0] > 10){
            System.out.println(-1);
            return;
        }

        Map<Integer, List<Integer>> map = new HashMap<>();
        for(int i=0; i< matrix[0].length; i++){
            reorganized(matrix, i, map);
        }

        // 重写排序方法
        List<Map.Entry<Integer, List<Integer>>> resultList = new ArrayList<>(map.entrySet());
        resultList.sort((o1, o2)->{
            int sum1 = o1.getValue().stream().reduce(Integer::sum).orElse(0);
            int sum2 = o2.getValue().stream().reduce(Integer::sum).orElse(0);
            if(sum1 != sum2){
                return sum2 - sum1;
            }else {
                for(int i=10; i>=1; i--){
                    int count1 = Collections.frequency(o1.getValue(), i);
                    int count2 = Collections.frequency(o2.getValue(), i);
                    if(count1 != count2){
                        return count2 - count1;
                    }
                }
            }
            return o2.getKey() - o1.getKey();
        });

        StringBuilder sb = new StringBuilder();
        int count = 0;
        for(Map.Entry<Integer, List<Integer>> resultMap : resultList){
            if(count<3){
                count++;
            }else {
                break;
            }
            sb.append(resultMap.getKey()).append(",");

        }
        System.out.println(sb.toString().replaceAll(",$", ""));

    }

    // 对矩阵列求和
    public static void reorganized(int[][] matrix, int n, Map<Integer, List<Integer>> map){
        List<Integer> list = new ArrayList<>();
        for (int[] ints : matrix) {
            list.add(ints[n]);
        }
        map.put(n+1, list);
    }
}
