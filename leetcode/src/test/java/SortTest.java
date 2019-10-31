import org.junit.Test;
import sorting.Insertion;
import sorting.Selection;
import sorting.Sort;

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
}
