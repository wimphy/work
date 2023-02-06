import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.my.kb.io.SiFiles.getInputStream;
import static com.my.kb.utils.SiString._toCap;

public class UnderScoreToCap {
    public static void main(String[] args) throws IOException {
        InputStream is = getInputStream("cas.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(_toCap(line));
        }
    }
}
