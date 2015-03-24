package com.booktera.android.common.devMode;

import android.app.Activity;
import android.util.Log;
import android.view.WindowManager;
import com.booktera.android.activities.LoginActivity;
import com.booktera.android.activities.RegisterActivity;
import com.booktera.android.common.UserData;
import com.booktera.android.common.utils.Utils;
import com.booktera.androidclientproxy.lib.proxy.Services;

import java.util.Date;
import java.util.Random;

class DevModeOn_Core implements IDevMode
{
    protected final String tag = ((Object) this).getClass().toString();

    private final String _userName = "ZomiDudu";
    private final String _password = "asdqwe123";
    private final int _userIdMock = 999;
    private final String _userNameAtRegister = "test";
    private final String _emailEndingAtRegister = "@test.com";

    @Override
    public void mockLogin()
    {
        Log.d(tag, "Mocking login...");

        // Mock login first, for the ActionBar to be in authenticated mode also the first time
        UserData.Instace.logIn(_userName, _userIdMock);

        // Log in really (will also get real userId) ...
        Services.Authentication.login(_userName, _password,/*persistent*/ false,
            (wasSuccessful, userId) -> {
                if (wasSuccessful)
                {
                    UserData.Instace.logIn(_userName, userId);
                }
                else
                {
                    // ... or log out, if something isn't ok
                    UserData.Instace.logOut();
                    Log.e(tag, "Mocked login haven't worked. ");
                }
            }
        );
    }
    @Override
    public void fillLoginForm(LoginActivity.ViewHolder vh)
    {
        Log.d(tag, "Filling the login form...");

        vh.userName.setText(_userName);
        vh.password.setText(_password);
    }
    @Override
    public void fillRegisterForm(RegisterActivity.ViewHolder vh)
    {
        Log.d(tag, "Filling the register form...");

        String password = Utils.ifNullOrEmpty(vh.password.getText().toString(), _password);
        vh.password.setText(password);
        vh.confirmPassword.setText(password);

        String userName = Utils.ifNullOrEmpty(vh.userName.getText().toString(), _userNameAtRegister);
        userName += new Random(new Date().getTime()).nextInt(123456);
        vh.userName.setText(userName);

        vh.email.setText(userName + _emailEndingAtRegister);
    }
    @Override
    public void hideTitleBar(Activity activity)
    {
        activity.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
    }
}
