import exercise.leetcode.ora.PalindromeNumber;
import exercise.leetcode.ora.ReverseInteger;
import exercise.leetcode.ora.RomanToInteger;
import exercise.leetcode.ora.TwoSum;
import org.junit.Assert;
import org.junit.Test;

public class LeetTest {
    @Test
    public void PalindromeNumber() {
        PalindromeNumber palindromeNumber = new PalindromeNumber();
        boolean b = palindromeNumber.isPalindrome(121);
        Assert.assertTrue(b);
    }

    @Test
    public void ReverseInteger() {
        ReverseInteger reverseInteger = new ReverseInteger();
        int i = reverseInteger.reverse(1463847412);
        Assert.assertEquals(i, 2147483641);
    }

    @Test
    public void RomanToInteger() {
        RomanToInteger romanToInteger = new RomanToInteger();
        int i = romanToInteger.romanToInt("IV");
        Assert.assertEquals(i, 4);

    }

    @Test
    public void TwoSum() {
        TwoSum twoSum = new TwoSum();
        int[] arr = twoSum.twoSum(new int[]{2, 3, 4, 5, 6}, 10);
        Assert.assertArrayEquals(arr, new int[]{2, 4});
    }
}
