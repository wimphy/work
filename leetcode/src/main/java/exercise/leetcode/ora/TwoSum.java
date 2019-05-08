package exercise.leetcode.ora;

import java.util.Arrays;

public class TwoSum {
    public int[] twoSum(int[] nums, int target) {
        int[] res = new int[]{0};
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] arr=new TwoSum().twoSum(new int[]{2, 7, 11, 15}, 9);
        System.out.println(Arrays.toString(arr));
    }
}

