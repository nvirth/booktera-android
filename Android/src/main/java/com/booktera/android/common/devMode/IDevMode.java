package com.booktera.android.common.devMode;


import android.app.Activity;
import com.booktera.android.activities.LoginActivity;
import com.booktera.android.activities.RegisterActivity;

public interface IDevMode
{
    void mockLogin();
    void fillLoginForm(LoginActivity.ViewHolder vh);
    void fillRegisterForm(RegisterActivity.ViewHolder vh);
    void hideTitleBar(Activity activity);
}
