package exercise.leetcode.ref;

import com.my.kb.io.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class MaximalSquare {
    private static final Logger log = LogManager.getLogger(MaximalSquare.class);

    @Test
    public void test1() {
        MaximalSquare maximalSquare = new MaximalSquare();
        char[][] matrix = new char[][]{
                {'1', '0', '1', '0', '0'},
                {'1', '0', '1', '1', '1'},
                {'1', '1', '1', '1', '1'},
                {'1', '0', '0', '1', '0'}
        };
        log.info(maximalSquare.maximalSquare(matrix));
    }

    @Test
    public void test2() throws Exception {
        MaximalSquare maximalSquare = new MaximalSquare();
        char[][] matrix = Utils.readMatrix("MaximalSquare.1.json");
        if (matrix == null) {
            throw new Exception("failed to read input source");
        }
        log.info(maximalSquare.maximalSquare(matrix));
    }

    public int maximalSquare(char[][] matrix) {
        int res = 0;
        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[0].length; y++) {
                int t = maxArea(x, y, matrix);
                if (t > res) {
                    res = t;
                }
            }
        }
        return res;
    }

    private int maxArea(int x, int y, char[][] matrix) {
        int len = 0;
        int res = 0;
        while (len < matrix.length - x && len < matrix[0].length - y) {
            if (isAllOne(x, y, matrix, len)) {
                int a = len + 1;
                if (a * a > res) {
                    res = a * a;
                }
                len++;
                continue;
            }

            return res;
        }
        return res;
    }

    private boolean isAllOne(int x, int y, char[][] matrix, int len) {
        for (int i = 0; i <= len; i++) {
            for (int j = 0; j <= len; j++) {
                if (matrix[x + i][y + j] != '1') {
                    return false;
                }
            }
        }
        return true;
    }
}
