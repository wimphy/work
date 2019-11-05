package sorting;

public class Merge implements Sort {
    @Override
    public void sort(int[] arr) {
        int[] tmp = new int[arr.length];
        mergeSort(arr, 0, arr.length, tmp);
    }

    private void mergeSort(int[] arr, int b, int e, int[] tmp) {
        if (e - b < 2) {
            return;
        }
        int m = (b + e) / 2;
        //left
        mergeSort(arr, b, m, tmp);
        //right
        mergeSort(arr, m, e, tmp);
        //merge
        mergeSort(arr, b, m, e, tmp);
    }

    private void mergeSort(int[] arr, int b, int m, int e, int[] tmp) {
        int i = b;
        int j = m;
        for (int k = b; k < e; k++) {
            if (j < e && (i >= m || arr[j] < arr[i])) {
                tmp[k] = arr[j];
                j++;
            } else {
                tmp[k] = arr[i];
                i++;
            }
        }
        for (int k = b; k < e; k++) {
            arr[k] = tmp[k];
        }
    }

    public void sort2(int[] arr) {
        int[] tmp = new int[arr.length];
        mergeSort2(arr, 2, tmp);
    }

    private void mergeSort2(int[] arr, int w, int[] tmp) {
        if (w / 2 > arr.length) {
            return;
        }
        int m, e;
        for (int i = 0; i < arr.length; i = i + w) {
            e = Math.min(i + w, arr.length);
            m = i + w / 2;
            mergeSort(arr, i, m, e, tmp);
        }
        mergeSort2(arr, 2 * w, tmp);
    }
}
