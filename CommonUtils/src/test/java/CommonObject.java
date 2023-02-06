import org.json.JSONObject;

public class CommonObject extends JSONObject {

    public int id() {
        return this.getInt("id");
    }

    public void id(int n) {
        this.put("id", n);
    }

    public String name() {
        return this.getString("name");
    }

    public CommonObject name(String n) {
        this.put("name", n);
        return this;
    }
}
