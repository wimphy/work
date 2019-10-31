package sorting;

public class Selection implements Sort {
    @Override
    public void sort(int[] arr) {
        if (arr.length <= 1) {
            return;
        }
        for (int i = 0; i < arr.length - 1; i++) {
            int min = arr[i];
            int pos = -1;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < min) {
                    min = arr[j];
                    pos = j;
                }
            }
            if (pos == -1) {
                continue;
            }
            //swap
            arr[pos] = arr[i];
            arr[i] = min;
        }
    }
}
