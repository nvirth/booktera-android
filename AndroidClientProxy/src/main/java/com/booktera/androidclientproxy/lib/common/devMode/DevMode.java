package com.booktera.androidclientproxy.lib.common.devMode;

import android.util.Log;
import com.booktera.androidclientproxy.lib.common.Config;


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

    // -- Enable/Disable flags
    public static class IsEnabled
    {
        // Leave DevMode flags in disabled state here
        public static boolean MockDataIfNoConnection = false;

        // Enable DevMode flags here
        static
        {
            if (Config.IsDebug)
            {
                MockDataIfNoConnection = true;
            }
        }
    }
}
