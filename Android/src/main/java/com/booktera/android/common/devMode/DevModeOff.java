package com.booktera.android.common.devMode;

import com.booktera.android.activities.LoginActivity;
import com.booktera.android.activities.RegisterActivity;

/**
 * Dummy class for if developer mode is off
 */
class DevModeOff implements IDevMode
{
    @Override
    public void mockLogin()
    {

    }
    @Override
    public void fillLoginForm(LoginActivity.ViewHolder vh)
    {

    }
    @Override
    public void fillRegisterForm(RegisterActivity.ViewHolder vh)
    {

    }
}
