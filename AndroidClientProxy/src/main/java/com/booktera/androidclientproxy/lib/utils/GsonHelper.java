package com.booktera.androidclientproxy.lib.utils;

import android.util.Log;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Norbert on 2015.03.06..
 */
public class GsonHelper
{
    public static Gson getGson()
    {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new DotNetDateDeserializer());
        return builder.create();
    }

    public static class DotNetDateDeserializer implements JsonDeserializer<Date>
    {
        protected final String tag = ((Object) this).getClass().toString();

        @Override
        public Date deserialize(JsonElement json, Type typfOfT, JsonDeserializationContext context)
        {
            try
            {
                String JSONDateToMilliseconds = "/(Date\\((.*?)(\\+.*)?\\))/";
                Pattern pattern = Pattern.compile(JSONDateToMilliseconds);
                Matcher matcher = pattern.matcher(json.getAsJsonPrimitive().getAsString());
                String result = matcher.replaceAll("$2");

                return new Date(Long.valueOf(result));
            }
            catch (Exception e)
            {
                Log.e(tag, "Couldn't parse the Date", e);
                return null;
            }
        }
    }
}