package exercise.leetcode.ora.bowling;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bowling {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Lock lock = new ReentrantLock(true);
        int playerCount = processPlayerCount(scanner);
        if (playerCount == 0) {
            System.out.print("Game ended");
            return;
        }
        var players = new ArrayList<Player>(playerCount);
        for (int i = 0; i < playerCount; i++) {
            players.add(new Player(i + 1, scanner, lock));
            Thread thread = new Thread(players.get(i));
            thread.start();
        }
    }

    private static int processPlayerCount(Scanner scanner) {
        int playerCount = 0;
        String code;
        System.out.print("Input the number of players (2-6): ");
        while ((code = scanner.nextLine()).length() == 1) {
            char c = code.charAt(0);
            if (c < '2' || c > '6') {
                System.out.print("Input the number of players (2-6): ");
                continue;
            }
            return Integer.valueOf(code);
        }
        return playerCount;
    }
}
