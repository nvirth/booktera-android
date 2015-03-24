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

    // -- Enable/Disable flags
    public static class IsEnabled
    {
        // Leave DevMode flags in disabled state here
        public static boolean MockLogin = false;
        public static boolean FillLoginForm = false;
        public static boolean FillRegisterForm = false;
        public static boolean HideTitleBar = false;

        // Enable DevMode flags here
        static
        {
            if (Config.IsDebug)
            {
                MockLogin = true;
                FillLoginForm = true;
                FillRegisterForm = true;
                HideTitleBar = true;
            }
        }
    }
}
