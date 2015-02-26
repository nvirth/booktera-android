package com.booktera.androidclientproxy.lib.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;

public class Utils
{
    public static String readToEnd(BufferedReader reader) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null)
            sb.append(line).append("\n");

        return sb.toString();
    }

    /**
     * Logs the msg as error, then throws a RuntimeException with it
     */
    public static void error(String msg, String tag)
    {
        RuntimeException e = new RuntimeException(msg);
        Log.e(tag, msg, e);
        throw e;
    }
}
