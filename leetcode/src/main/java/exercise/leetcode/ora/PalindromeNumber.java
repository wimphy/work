package exercise.leetcode.ora;

public class PalindromeNumber {
    public boolean isPalindrome(int x) {
        String s = String.valueOf(x);
        for (int i = 0, j = s.length() - 1; i < j && i + j < s.length(); i++, j--) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(new PalindromeNumber().isPalindrome(121));
    }
}
