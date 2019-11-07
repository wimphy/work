import org.junit.Assert;
import org.junit.Test;
import sorting.*;

import java.util.Arrays;
import java.util.Random;

import static com.my.kb.utils.EasyLogger.log;

public class SortTest {
    @Test
    public void InsertionTest() {
        Sort sort = new Insertion();
        int[] arr = {9, 8, 7, 5, 4, 6, 1, 2, 3};
        log(Arrays.toString(arr));
        sort.sort(arr);
        log(Arrays.toString(arr));
    }

    @Test
    public void InsertionTestBig() {
        Sort sort = new Insertion();
        int count = 1000000;
        int[] arr = new int[count];
        Random r = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = r.nextInt(count);
        }
        log(arr);
        sort.sort(arr);
        log(arr); //9:26
    }

    @Test
    public void SelectionTest() {
        Sort sort = new Selection();
        int[] arr = {9, 8, 7, 5, 4, 6, 1, 2, 3};
        log(Arrays.toString(arr));
        sort.sort(arr);
        log(Arrays.toString(arr));
    }

    @Test
    public void SelectionTestBig() {
        Sort sort = new Selection();
        int count = 1000000;
        int[] arr = new int[count];
        Random r = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = r.nextInt(count);
        }
        log(arr);
        sort.sort(arr);
        log(arr); //6:35
    }

    @Test
    public void MergeTest() {
        Sort sort = new Merge();
        int[] arr = {9, 8, 7, 5, 4, 6, 1, 2, 3};
        log(Arrays.toString(arr));
        sort.sort(arr);
        log(Arrays.toString(arr));
    }

    @Test
    public void MergeTestBig() {
        Sort sort = new Merge();
        int count = 1000000;
        int[] arr = new int[count];
        Random r = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = r.nextInt(count);
        }
        log(arr);
        long start = System.currentTimeMillis();
        sort.sort(arr);
        log("time(ms): " + (System.currentTimeMillis() - start));
        log(arr); //00:00.213
    }

    @Test
    public void Merge2Test() {
        Merge sort = new Merge();
        int[] arr = {9, 8, 7, 5, 4, 6, 1, 2, 3};
        log(Arrays.toString(arr));
        sort.sort2(arr);
        log(Arrays.toString(arr));
    }

    @Test
    public void Merge2TestBig() {
        Merge sort = new Merge();
        int count = 1000000;
        int[] arr = new int[count];
        Random r = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = r.nextInt(count);
        }
        log(arr);
        long start = System.currentTimeMillis();
        sort.sort2(arr);
        log("time(ms): " + (System.currentTimeMillis() - start));
        log(arr); //00:00.293
    }

    @Test
    public void HeapTest() {
        Sort sort = new Heap();
        int[] arr = {1,9, 4, 20, 23,15,14,99,2};
        log(Arrays.toString(arr));
        sort.sort(arr);
        log(Arrays.toString(arr));
    }

    @Test
    public void HeapTestBig() {
        Sort sort = new Heap();
        int count = 1000000;
        int[] arr = new int[count];
        Random r = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = r.nextInt(count);
        }
        log(arr);
        long start = System.currentTimeMillis();
        sort.sort(arr);
        log("time(ms): " + (System.currentTimeMillis() - start));
        log(arr); //00:00.499
        assertInOrder(arr);
    }

    //small to big
    private void assertInOrder(int[] arr) {
        Assert.assertNotNull(arr);
        Assert.assertTrue(arr.length > 0);
        int[] x = {0, arr[0]};
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < x[1]) {
                log(String.format("[%d]:{%d} > [{%d}]:{%d}", x[0], x[1], i, arr[i]));
                Assert.fail();
            }
            x[0] = i;
            x[1] = arr[i];
        }
    }
}
