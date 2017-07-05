package com.test.test.data.network;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateDeserializer implements JsonDeserializer<Date>{

    private final String[] formatStrings = {"yyyy-MM-dd", "dd-MM-yyyy"};

    private final List<DateFormat> formats = new ArrayList<>();


    public DateDeserializer() {
        for(String formatStr : formatStrings) {
            DateFormat format = new SimpleDateFormat(formatStr);
            format.setLenient(false);
            formats.add(format);
        }
    }

    private Date parseDate(String dateStr) {
        Date date = null;
        if(dateStr != null && !dateStr.isEmpty()) {
            for (DateFormat dateFormat : formats) {
                try {
                    date = dateFormat.parse(dateStr);
                    break;
                } catch (ParseException e) {
                    //пустой
                }
            }
        }
        return date;
    }


    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String dateStr = json.getAsString();
        return parseDate(dateStr);
    }
}
