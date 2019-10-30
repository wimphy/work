package sorting;

public class Insertion implements Sort {
    @Override
    public void sort(int[] arr) {
        if (arr.length == 1) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            int x = arr[i];
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] > x) {
                    arr[j + 1] = arr[j];
                    arr[j] = x;
                }
            }
        }
    }
}
