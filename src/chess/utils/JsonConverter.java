package chess.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class JsonConverter {

    private Gson gson;

    public JsonConverter(Gson gson) {
        this.gson = gson;
    }

    public String toJson(Object object) {
        return this.gson.toJson(object);
    }

    public Object toObject(String json, Type type) {
        return this.gson.fromJson(json, type);
    }
}