package sorting;

public class Heap extends AbstractSort {
    @Override
    public void sort(int[] arr) {
        int end = arr.length;
        buildHeap(arr);
        while (end-- > 0) {
            swap(arr, 0, end);
            buildHeap(arr, 0, end);
        }
    }

    private void buildHeap(int[] arr) {
        if (arr.length < 1) {
            return;
        }
        //the last non-leaf node
        for (int i = (arr.length - 1) / 2; i >= 0; i--) {
            buildHeap(arr, i, arr.length);
        }
    }

    //inorder traversal
    private void buildHeap(int[] arr, int index, int len) {
        if (len > arr.length || index >= len || index < 0) {
            return;
        }
        int left = index * 2 + 1;
        int largest = index;
        if (left < len && arr[left] > arr[largest]) {
            largest = left;
        }
        int right = left + 1;
        if (right < len && arr[right] > arr[largest]) {
            largest = right;
        }
        if (largest != index) {
            swap(arr, largest, index);
            buildHeap(arr, largest, len);
        }
    }
}
