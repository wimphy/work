package exercise.leetcode.ora.bowling;

import java.util.ArrayList;
import java.util.List;

class BowlingTurn {
    private int plus = -1;
    private List<Integer> kicks = new ArrayList<>();
    private int score = -1;

    int getPlus() {
        if (plus > -1) {
            return plus;
        }
        plus = 0;
        if (kicks.size() == 1) {
            plus = 2;
        } else if (kicks.size() == 2 && kicks.get(0) + kicks.get(1) == 10) {
            plus = 1;
        }
        return plus;
    }

    List<Integer> getKicks() {
        return kicks;
    }

    int getScore() {
        return score;
    }

    void setScore(int score) {
        this.score = score;
    }
}
