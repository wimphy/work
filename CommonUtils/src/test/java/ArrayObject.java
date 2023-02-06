import org.json.JSONArray;
import org.json.JSONObject;

public class ArrayObject extends SingleObject {

    public ArrayObject(String name, JSONArray arr) {
        if (arr == null || arr.length() < 1) {
            return;
        }
        this.name(name);
        this.put("value", createValueArr(arr));
    }

    public JSONArray createValueArr(JSONArray arr) {
        JSONArray res = new JSONArray();
        for (int i = 0; i < arr.length(); i++) {
            Object v = arr.get(i);
            if (v instanceof JSONArray) {
                res.put(createValueArr((JSONArray)v));
            } else if (v instanceof JSONObject) {
                res.put(createValueObj((JSONObject)v));
            } else {
                res.put(new ValueObject(v));
            }
        }
        return res;
    }
}
