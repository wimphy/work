import org.json.JSONArray;
import org.json.JSONObject;

public class CommonRequest extends JSONObject {
    public CommonRequest() {
        this.put("status", "draft");
        this.put("objects", new JSONArray());
    }

    public JSONArray objects() {
        return this.getJSONArray("objects");
    }
}
