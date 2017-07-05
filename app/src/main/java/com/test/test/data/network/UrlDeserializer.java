package com.test.test.data.network;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlDeserializer implements JsonDeserializer<URL>{
    @Override
    public URL deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String urlStr = json.getAsString();
        if(urlStr == null || urlStr.isEmpty()) {
            return null;
        }
        else {
            try {
                return new URL(urlStr);
            }
            catch (MalformedURLException e) {
                throw new JsonParseException(e);
            }
        }
    }
}
