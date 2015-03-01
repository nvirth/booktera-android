package com.booktera.android;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import com.booktera.android.activities.LoginActivity;
import com.booktera.androidclientproxy.lib.proxy.base.RestServiceClientBase;

/**
 * Created by Norbert on 2015.02.06..
 */
public class BookteraApplication extends Application
{
    private static Application _this;

    @Override
    public void onCreate()
    {
        super.onCreate();
        _this = this;

        InitRestServiceClientBase();

        // Because we sometimes can't get even a StackTrace while observing an unhandled exception during debugging
        Thread.setDefaultUncaughtExceptionHandler((thread, ex) ->
            Log.e(this.getClass().toString(), "Unhandled exception occurred", ex));
    }

    /**
     * Inits the RestServiceClientBase class with a defaultErrorMsg action
     */
    private void InitRestServiceClientBase()
    {
        // A Handler by default post messages to the thread where it is created.
        // We are now on the (main) UI thread
        Handler handler = new Handler();

        RestServiceClientBase.setSendErrorMsgAction(
            (errorMsg) -> handler.post(
                () -> Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show()
            ));
        RestServiceClientBase.setResources(getAppResources());
        RestServiceClientBase.setRedirectToLoginAction(
            () -> handler.post(() ->
                    startActivity(new Intent(this, LoginActivity.class))
            ));
    }

    public static Resources getAppResources()
    {
        return _this.getResources();
    }

    public static Context getAppContext()
    {
        return _this.getApplicationContext();
    }
}
