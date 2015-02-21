package com.booktera.androidclientproxy.lib.utils;

import java.io.BufferedReader;
import java.io.IOException;

public class Helpers
{
    public static String readToEnd(BufferedReader reader) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null)
            sb.append(line).append("\n");

        return sb.toString();
    }
}
