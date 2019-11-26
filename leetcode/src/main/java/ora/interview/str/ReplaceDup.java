package ora.interview.str;

import java.util.Set;
import java.util.TreeSet;

import static com.my.kb.utils.EasyLogger.log;

public class ReplaceDup {
    public String replace(String str) {
        Set<Character> chars = new TreeSet<>();
        for (int i = 0; i < str.length(); i++) {
            chars.add(str.charAt(i));
        }
        StringBuilder sb = new StringBuilder();
        for (Character c : chars) {
            sb.append(c);
        }

        return sb.toString();
    }

    public String insertionReplace(String str) {
        if (str.length() < 2) {
            return str;
        }
        char[] chars = str.toCharArray();
        int end = chars.length;
        int start = 0;
        StringBuilder res = new StringBuilder();

        while (start < end) {
            boolean found = false;
            for (int j = start - 1; j >= 0; j--) {
                if (chars[start] == chars[j]) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                res.append(chars[start]);
                start++;
                continue;
            }
            for (int k = start; k < chars.length - 1; k++) {
                chars[k] = chars[k + 1];
            }
            end--;
            //chars[end]='\0';
        }

        return res.toString();
    }
}
