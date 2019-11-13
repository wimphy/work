package sorting;

public class Insertion implements Sort {
    @Override
    public void sort(int[] arr) {
        if (arr.length == 1) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            int x = arr[i];
            int j = i;
            while (j > 0 && arr[j-1] > x) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = x;
        }
    }
}
