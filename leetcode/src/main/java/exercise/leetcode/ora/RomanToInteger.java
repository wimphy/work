package exercise.leetcode.ora;

/*
Symbol       Value
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
* */
public class RomanToInteger {
    public int romanToInt(String s) {
        int res = 0;
        char[] cs = s.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            if (cs[i] == 'M') {
                res += 1000;
            } else if (cs[i] == 'C') {
                if (i + 1 < cs.length && cs[i + 1] == 'M') {
                    res += 900;
                    i++;
                } else if (i + 1 < cs.length && cs[i + 1] == 'D') {
                    i++;
                    res += 400;
                } else {
                    res += 100;
                }
            } else if (cs[i] == 'D') {
                res += 500;
            } else if (cs[i] == 'X') {
                if (i + 1 < cs.length && cs[i + 1] == 'C') {
                    i++;
                    res += 90;
                } else if (i + 1 < cs.length && cs[i + 1] == 'L') {
                    res += 40;
                    i++;
                } else {
                    res += 10;
                }
            } else if (cs[i] == 'L') {
                res += 50;
            } else if (cs[i] == 'I') {
                if (i + 1 < cs.length && cs[i + 1] == 'X') {
                    i++;
                    res += 9;
                } else if (i + 1 < cs.length && cs[i + 1] == 'V') {
                    i++;
                    res += 4;
                } else {
                    res += 1;
                }
            } else if (cs[i] == 'V') {
                res += 5;
            }
        }
        return res;
    }
}
