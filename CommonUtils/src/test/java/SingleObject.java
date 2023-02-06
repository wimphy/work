import org.json.JSONArray;
import org.json.JSONObject;

public class SingleObject extends CommonObject {
    protected SingleObject() {

    }

    public SingleObject(String name, JSONObject obj) {
        if (obj == null || obj.length() < 1) {
            return;
        }
        this.name(name);
        this.put("value", createValueObj(obj));
    }

    protected JSONObject createValueObj(JSONObject obj) {
        JSONObject value = new JSONObject();
        JSONArray attributes = new JSONArray();
        value.put("attributes", attributes);
        for (String k : obj.keySet()) {
            Object v = obj.get(k);
            if (v instanceof JSONArray) {
                attributes.put(new ArrayObject(k, (JSONArray) v));
            } else if (v instanceof JSONObject) {
                attributes.put(new SingleObject(k, (JSONObject) v));
            } else {
                attributes.put(new ValueObject(v).name(k));
            }
        }
        return value;
    }
}
