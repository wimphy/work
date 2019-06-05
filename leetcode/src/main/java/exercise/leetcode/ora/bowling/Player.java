package exercise.leetcode.ora.bowling;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;

public class Player implements Runnable {
    private static final char STRIKE = 'X';
    private static final char SPARE = '/';
    private static final char NEW = 'N';
    private static final char END = 'E';
    private static char STATUS = 'P';

    private int score = 0;
    private List<BowlingTurn> turns = new ArrayList<>();
    private int id;
    private Scanner inputScanner;
    private Lock lock;
    private String info;

    Player(int id, Scanner inputScanner, Lock lock) {
        this.id = id;
        this.lock = lock;
        this.inputScanner = inputScanner;
    }

    @Override
    public void run() {
        while (STATUS != END && turns.size() < 10) {
            lock.lock();
            BowlingTurn bowlingTurn = new BowlingTurn();
            turns.add(bowlingTurn);
            info = "input the pin of knocked down (0-9, X, /): ";
            char code = getCode();
            if (code == END) {
                STATUS = END;
                System.out.println("Ending game: " + this);
                lock.unlock();
                break;
            } else if (code == STRIKE) {
                bowlingTurn.getKicks().add(10);
            } else {
                processSpare(bowlingTurn, code);
            }
            if (turns.size() == 10) {
                processTen(bowlingTurn);
            }
            calcScore();
            System.out.println(this);
            lock.unlock();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "Turn" + turns.size() + ", Player" + this.id + ", score: " + this.score;
    }

    private char getCode() {
        return getCode(false);
    }

    private char getCode(boolean numberOnly) {
        while (true) {
            if (STATUS == END) {
                return END;
            }
            System.out.print(String.format("(Turn%d, Player%d) - ", turns.size(), id));
            System.out.print(info);
            String code = inputScanner.nextLine();
            if (code.length() != 1) {
                continue;
            }
            char c = code.toUpperCase().charAt(0);
            boolean isCharCode = c == NEW || c == END || c == STRIKE || c == SPARE;
            boolean isNumber = c >= '0' && c <= '9';
            if (numberOnly) {
                if (isNumber) {
                    return c;
                }
            } else {
                if (isNumber || isCharCode) {
                    return c;
                }
            }
            return END;
        }
    }

    private char getNumberCode(char c) {
        if (c >= '0' && c <= '9') {
            return c;
        }
        return getCode(true);
    }

    private void setKick(BowlingTurn bowlingTurn, char code) {
        if (code == STRIKE) {
            bowlingTurn.getKicks().add(10);
        } else if (code == SPARE) {
            int prev = bowlingTurn.getKicks().get(bowlingTurn.getKicks().size() - 1);
            bowlingTurn.getKicks().add(10 - prev);
        } else {
            bowlingTurn.getKicks().add(code - '0');
        }
    }

    private void processSpare(BowlingTurn bowlingTurn, char code) {
        info = "input the pin of knocked down (0-9): ";
        code = getNumberCode(code);
        setKick(bowlingTurn, code);
        info = "input the pin of knocked down (0-9, /): ";
        code = getCode();
        if (code == SPARE) {
            setKick(bowlingTurn, code);
        } else {
            code = getNumberCode(code);
            setKick(bowlingTurn, code);
        }
    }

    private void processTen(BowlingTurn bowlingTurn) {
        char code;
        if (bowlingTurn.getPlus() == 2) {
            info = "input the pin of knocked down (0-9, X, /): ";
            code = getCode();
            if (code == STRIKE) {
                bowlingTurn.getKicks().add(10);
                code = getCode();
                setKick(bowlingTurn, code);
            } else {
                processSpare(bowlingTurn, code);
            }

        } else if (bowlingTurn.getPlus() == 1) {
            info = "input the pin of knocked down (0-9, X, /): ";
            code = getCode();
            setKick(bowlingTurn, code);
        }
    }

    private void calcScore() {
        for (int i = 0; i < turns.size(); i++) {
            BowlingTurn bowlingTurn = turns.get(i);
            int turnScore = bowlingTurn.getScore();
            if (turnScore > -1) {
                continue;
            }
            int plus = i == 9 ? 0 : bowlingTurn.getPlus();
            turnScore = 0;
            BowlingTurn nextTurn1 = turns.size() > i + 1 ? turns.get(i + 1) : null;
            BowlingTurn nextTurn2 = turns.size() > i + 2 ? turns.get(i + 2) : null;
            switch (plus) {
                case 2:
                    var kicks = new ArrayList<Integer>();
                    if (nextTurn1 != null) {
                        kicks.addAll(nextTurn1.getKicks());
                    }
                    if (nextTurn2 != null) {
                        kicks.addAll(nextTurn2.getKicks());
                    }
                    if (kicks.size() < 2) {
                        return;
                    }
                    turnScore += kicks.get(0);
                    turnScore += kicks.get(1);
                    break;
                case 1:
                    if (nextTurn1 == null) {
                        return;
                    }
                    turnScore += nextTurn1.getKicks().get(0);
                    break;
                case 0:
                    break;
            }
            for (int kick : bowlingTurn.getKicks()) {
                turnScore += kick;
            }
            bowlingTurn.setScore(turnScore);
            score += turnScore;
        }
    }
}
