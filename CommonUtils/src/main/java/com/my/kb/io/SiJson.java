package com.my.kb.io;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SiJson implements Iterable<SiJson> {
    private static final Logger log = LogManager.getLogger(SiJson.class);
    private static final String PathSeparator = "/";

    public static SiJson from(Object source) {
        return new SiJson(source);
    }

    public static SiJson readFromResource(String name) throws IOException {
        return new SiJson(SiFiles.readFromResource(name));
    }

    public static SiJson readFromPath(String path) throws IOException {
        return new SiJson(SiFiles.read(path));
    }

    public static SiJson empty() {
        return new SiJson();
    }

    private Object object = null;
    private String name = null;
    private String path = "";

    public SiJson() {
    }

    protected SiJson(Object source, String name) {
        init(source, name);
    }

    public SiJson(Object source) {
        init(source);
    }

    public SiJson newChild(String key, Object data) {
        String childPath = this.path + PathSeparator + key;
        SiJson child = new SiJson(data, key);
        child.setPath(childPath);
        return child;
    }

    public void put(String key, Object value) {
        if (value == null || key == null || key.length() < 1) {
            return;
        }
        if (isNull()) {
            this.object = new JSONObject();
        } else if (!isObject()) {
            return;
        }
        JSONObject obj = (JSONObject) this.object;
        if (value instanceof SiJson valueNode) {
            if (valueNode.isObject()) {
                obj.put(key, valueNode.getJSONObject());
            } else if (valueNode.isArray()) {
                obj.put(key, valueNode.getJSONArray());
            } else {
                obj.put(key, valueNode.getValue());
            }
        } else {
            obj.put(key, value);
        }
    }

    public void put(Object value) {
        if (value == null) {
            return;
        }
        if (isNull()) {
            this.object = new JSONArray();
        } else if (!isArray()) {
            return;
        }
        JSONArray arr = (JSONArray) this.object;
        if (value instanceof SiJson valueNode) {
            if (valueNode.isObject()) {
                arr.put(valueNode.getJSONObject());
            }
            return;
        }
        arr.put(value);
    }

    public boolean has(String fieldName) {
        return fieldName != null && isObject() && ((JSONObject) this.object).has(fieldName);
    }

    public SiJson getChild(int index) {
        if (!isArray()) {
            return SiJson.empty();
        }
        Object child = ((JSONArray) this.object).get(index);
        return newChild(String.valueOf(index), child);
    }

    public SiJson getChild(String fieldName) {
        Object child = getChildValue(fieldName);
        if (child == null) {
            return SiJson.empty();
        }

        return newChild(fieldName, child);
    }

    public Object getChildValue(String fieldName) {
        Object child = null;
        if (isArray()) {
            try {
                int i = Integer.parseInt(fieldName);
                child = ((JSONArray) this.object).get(i);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                log.debug("incorrect index of array", e);
            }
        } else {
            if (has(fieldName)) {
                child = ((JSONObject) this.object).get(fieldName);
            }
        }
        return child;
    }

    public String getString(String fieldName) {
        Object child = getChildValue(fieldName);
        if (child == null) {
            return null;
        }
        return child.toString();
    }

    public SiJson at(String jsonPtrExpr) {
        if (jsonPtrExpr == null || jsonPtrExpr.length() < 1) {
            return SiJson.empty();
        }
        String p = jsonPtrExpr.trim();
        if (p.startsWith(PathSeparator)) {
            p = p.substring(1);
        }
        if (p.length() < 1) {
            return SiJson.empty();
        }
        String[] names = p.split(PathSeparator);
        SiJson child = this;
        for (String name : names) {
            child = child.getChild(name);
            if (child.isNull()) {
                break;
            }
        }
        return child;
    }

    public Object getValue() {
        return this.object;
    }

    public JSONObject getJSONObject() {
        if (!isObject()) {
            return null;
        }
        return (JSONObject) this.object;
    }

    public JSONArray getJSONArray() {
        if (!isArray()) {
            return null;
        }
        return (JSONArray) this.object;
    }

    public void clearAndPutAll(SiJson arr) {
        if (!arr.isArray()) {
            return;
        }
        JSONArray thisArr = (JSONArray) this.object;
        while (thisArr.length() > 0) {
            thisArr.remove(0);
        }
        thisArr.putAll(arr.getJSONArray());
    }

    public void addEle(String eleName) {
        if (!isArray() || eleName == null || eleName.length() < 1) {
            return;
        }

        JSONArray arr = new JSONArray();
        for (SiJson child : this) {
            JSONObject newChild = new JSONObject();
            newChild.put(eleName, child.getJSONObject());
            arr.put(newChild);
        }
        this.object = arr;
    }

    @Override
    public String toString() {
        return isNull() ? "" : object.toString();
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        if (name != null) {
            return name;
        }
        if (path != null && path.indexOf('/') > -1) {
            name = path.substring(path.lastIndexOf('/') + 1);
        }
        return name;
    }

    public boolean isNull() {
        return this.object == null;
    }

    public boolean isValueNode() {
        return !isNull() && !(this.object instanceof JSONObject) && !(this.object instanceof JSONArray);
    }

    public boolean isArray() {
        return !isNull() && this.object instanceof JSONArray;
    }

    public boolean isObject() {
        return !isNull() && this.object instanceof JSONObject;
    }

    public boolean isValueArray() {
        if (isNull() || !isArray()) {
            return false;
        }

        for (SiJson child : this) {
            if (!child.isValueNode()) {
                return false;
            }
        }

        return true;
    }

    public int length() {
        if (isArray()) {
            return ((JSONArray) object).length();
        } else if (isObject()) {
            return ((JSONObject) object).length();
        } else {
            return 0;
        }
    }

    @Override
    public Iterator<SiJson> iterator() {
        if (isNull()) {
            return Collections.emptyIterator();
        }

        return new SiJsonIterator();
    }

    private class SiJsonIterator implements Iterator<SiJson> {
        private final Iterator<String> it;

        public SiJsonIterator() {
            if (isObject()) {
                JSONObject obj = (JSONObject) object;
                it = obj.keys();
            } else if (isArray()) {
                JSONArray arr = (JSONArray) object;
                List<String> list = new ArrayList<>();
                for (int i = 0; i < arr.length(); i++) {
                    list.add(String.valueOf(i));
                }
                it = list.iterator();
            } else {
                it = Collections.emptyIterator();
            }
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public SiJson next() {
            String k = it.next();
            Object child = getChildValue(k);
            return newChild(k, child);
        }
    }

    //region private functions

    private void init(Object source) {
        if (source == null) {
            this.object = new JSONObject();
            return;
        }
        String str = source.toString().trim();
        boolean isArr = str.startsWith("[");
        if (isArr) {
            this.object = new JSONArray(str);
            return;
        }

        boolean isObj = str.startsWith("{");
        if (isObj) {
            this.object = new JSONObject(str);
            return;
        }

        this.object = source;
    }

    private void init(Object source, String name) {
        if (source == null || name == null) {
            this.object = new JSONObject();
            return;
        }
        this.name = name;
        init(source);
    }

    protected void setPath(String path) {
        this.path = path;
    }
    //endregion
}
