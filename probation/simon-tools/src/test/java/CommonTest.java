import org.testng.annotations.Test;

public class CommonTest {
    @Test
    public void testStringFormat() {
        String s = String.format("%1$s, world, %1$s", "hello");
        System.out.println(s);
    }
}
