package com.stanlong.leetcode;

/**
 * 整数转罗马数字
 */
public class LeetCode {

    public static void main(String[] args) {
        Solution solution = new Solution();
        int num = 3;
        String result = solution.maxArea(num);
        System.out.println(result);
    }
}

class Solution{
    public String maxArea(int num){
        int[] values = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String[] reps = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<13; i++){
            while(num>=values[i]){
                num -= values[i];
                sb.append(reps[i]);
            }
        }
        return sb.toString();
    }
}