import ora.interview.str.ReplaceDup;
import org.junit.Test;

import static com.my.kb.utils.SiLogger.log;

public class StringTest {
    @Test
    public void testReplaceDup() throws Exception {
        ReplaceDup rd = new ReplaceDup();
        String res = rd.replace("Hello World");
        log(res);
    }

    @Test
    public void testInsertionReplace() throws Exception {
        ReplaceDup rd = new ReplaceDup();
        String res = rd.insertionReplace("Hello World");
        log(res);
    }
}
