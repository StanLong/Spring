package com.stanlong.leetcode;

/**
 * 盛最多水的容器
 */
public class LeetCode {

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] height = {10,9,8,7,6,5,4,3,2,1};
        int result = solution.maxArea(height);
        System.out.println(result);
    }
}

class Solution{
    public int maxArea(int[] height){
        int len = height.length;
        int res = 0;
        for(int i = 0; i < len - 1; i++) {
            for(int j = i + 1; j < len; j++) {
                int a = height[i];
                int b = height[j];
                int c = j - i;
                if (a > b){
                    if (res < b * c)
                        res = b * c;
                }else{
                    if (res < a *c)
                        res = a * c;
                }
            }
        }
        return res;
    }
}