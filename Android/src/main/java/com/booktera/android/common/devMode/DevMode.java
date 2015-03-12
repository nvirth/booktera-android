package com.booktera.android.common.devMode;

import android.util.Log;
import com.booktera.android.common.Config;


public abstract class DevMode implements IDevMode
{
    private static final String tag = DevMode.class.toString();

    public static IDevMode Instance = createNew();

    protected DevMode()
    {
    }

    protected static IDevMode createNew()
    {
        if (Config.IsDebug)
        {
            Log.w(tag, "Developer mode is ON. ");
            return new DevModeOn(new DevModeOn_Core());
        }
        else
        {
            return new DevModeOff();
        }
    }
}
