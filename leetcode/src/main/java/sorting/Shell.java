package sorting;

import java.util.ArrayList;

public class Shell extends Insertion {
    @Override
    public void sort(int[] arr) {
        ArrayList<Integer> gaps = new ArrayList<>(16);
        int n = arr.length;
        while (n > 0) {
            n = n / 10;
            gaps.add(n);
        }
        if (!gaps.contains(1)) {
            gaps.add(1);
        }
        for (int gap : gaps) {
            for (int b = 0; b < arr.length; b = b + gap) {
                int x = arr[b];
                int s = b;
                while (s >= gap && x < arr[s - gap]) {
                    arr[s] = arr[s - gap];
                    s -= gap;
                }
                arr[s] = x;
            }
        }
    }
}
