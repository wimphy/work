package leetcode.problems;

import org.junit.Test;

public class CommonPrefix {
    @Test
    public void testLongestCommonPrefix() {
        String s = longestCommonPrefix(new String[]{"a", "a", "ab"});
        System.out.println("14. Longest Common Prefix: " + s);

        s = longestCommonPrefix(new String[]{"a", "a", "b"});
        System.out.println("14. Longest Common Prefix: " + s);

        s = longestCommonPrefix(new String[]{"abab", "aba", ""});
        System.out.println("14. Longest Common Prefix: " + s);

        s = longestCommonPrefix(new String[]{"", "", ""});
        System.out.println("14. Longest Common Prefix: " + s);
    }

    public String longestCommonPrefix(String[] strs) {
        StringBuilder res = new StringBuilder();
        if (strs.length == 1) {
            return strs[0];
        } else if (strs.length == 0) {
            return res.toString();
        }

        int w = strs[0].length();
        final int h = strs.length;

        int i = 0;
        while (i < w) {
            String col0 = strs[0].substring(i, i + 1);
            for (int j = 1; j < h; j++) {
                if (strs[j].length() < w) {
                    w = strs[j].length();
                }
                if (strs[j].length() < i + 1) {
                    return res.toString();
                }
                String col = strs[j].substring(i, i + 1);
                if (!col0.equals(col)) {
                    return res.toString();
                }
            }
            res.append(col0);
            i++;
        }

        return res.toString();
    }
}
