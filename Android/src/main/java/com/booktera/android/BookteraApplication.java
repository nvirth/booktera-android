package com.booktera.android;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import com.booktera.android.activities.LoginActivity;
import com.booktera.android.common.Config;
import com.booktera.android.common.UserData;
import com.booktera.android.common.devMode.DevMode;
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

        InitProxyLib();

        // Because we sometimes can't get even a StackTrace while observing an unhandled exception during debugging
        Thread.setDefaultUncaughtExceptionHandler((thread, ex) ->
            Log.e(this.getClass().toString(), "Unhandled exception occurred", ex));

        DevMode.Instance.mockLogin();
    }

    /**
     * Initializes the AndroidClientProxy lib's RestServiceClientBase and Config classes
     */
    private void InitProxyLib()
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
            () -> handler.post(() -> {
                // First log out the user, its session timed out
                UserData.Instace.logOut();

                // Then redirect to the login page
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }));

        // Setting up the lib's developer mode configuration, to be the same as the app's
        com.booktera.androidclientproxy.lib.common.Config.IsDebug = Config.IsDebug;
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
