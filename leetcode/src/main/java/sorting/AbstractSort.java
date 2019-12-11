package sorting;

public abstract class AbstractSort implements Sort {
    protected void swap(int[] arr, int big, int small) {
        int tmp = arr[big];
        arr[big] = arr[small];
        arr[small] = tmp;
    }
    //test
}
