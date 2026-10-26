package com.fly.demo.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GsonUtil {

    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final static Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Integer.class, (JsonDeserializer<Integer>) (jsonElement, type, jsonDeserializationContext) -> {
                return jsonElement.getAsInt(); // 强制转为整数
            })
            .registerTypeAdapter(Number.class, new JsonDeserializer<Number>() {
                @Override
                public Number deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                        throws JsonParseException {
                    String numStr = json.getAsString();
                    if (numStr.contains(".")) {
                        return json.getAsDouble();
                    } else {
                        return json.getAsLong(); // 或者 getAsInt() 根据需求
                    }
                }
            })
            .registerTypeAdapter(LocalDateTime.class, new TypeAdapter<LocalDateTime>() {
                @Override
                public void write(JsonWriter out, LocalDateTime value) throws IOException {
                    if (value == null) {
                        out.nullValue();
                        return;
                    }
                    String formattedDate = DATE_TIME_FORMATTER.format(value);
                    out.value(formattedDate);
                }

                @Override
                public LocalDateTime read(JsonReader in) throws IOException {
                    if (in.peek() == null) {
                        return null;
                    }
                    String date = in.nextString();
                    try {
                        return LocalDateTime.parse(date, DATE_TIME_FORMATTER);
                    } catch (Exception e) {
                        throw new IOException("Unable to parse date", e);
                    }
                }
            })
            .registerTypeAdapter(LocalDate.class, new TypeAdapter<LocalDate>() {
                @Override
                public void write(JsonWriter out, LocalDate value) throws IOException {
                    if (value == null) {
                        out.nullValue();
                        return;
                    }
                    String formattedDate = DATE_FORMATTER.format(value);
                    out.value(formattedDate);
                }

                @Override
                public LocalDate read(JsonReader in) throws IOException {
                    if (in.peek() == null) {
                        return null;
                    }
                    String date = in.nextString();
                    try {
                        return LocalDate.parse(date, DATE_FORMATTER);
                    } catch (Exception e) {
                        throw new IOException("Unable to parse date", e);
                    }
                }
            })
            .create();

    public static Gson getGson() {
        return GSON;
    }

    public static String toJson(Object o) {
        return GSON.toJson(o);
    }

    public static <T> T fromJson(String json, TypeToken<T> typeToken) {
        return GSON.fromJson(json, typeToken);
    }
}
