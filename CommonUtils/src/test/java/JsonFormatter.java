import com.my.kb.io.SiJson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonFormatter {
    private int id = 1;

    public void run() throws IOException {
        CommonRequest request = new CommonRequest();
        JSONArray objects = request.objects();
        JSONObject src = SiJson.readFromResource("create.event.json").getJSONObject();
        for (String level1 : src.keySet()) {
            if (level1.equals("corporateAction")) {
                objects.put(new SingleObject("corporateAction", src.getJSONObject(level1)));
            } else {
                objects.put(new ArrayObject("corporateActionSecurity", src.getJSONArray(level1)));
            }
        }
        Files.write(Paths.get("C:\\simon\\lseg\\corax\\docs\\tmp\\submit2.json"),
                request.toString().getBytes(StandardCharsets.UTF_8));
    }

    public static void main(String[] args) throws IOException {
        new JsonFormatter().run();
        System.out.println("done");
    }
}
