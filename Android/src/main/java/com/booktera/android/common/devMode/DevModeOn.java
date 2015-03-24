package com.booktera.android.common.devMode;

import android.app.Activity;
import com.booktera.android.activities.LoginActivity;
import com.booktera.android.activities.RegisterActivity;

/**
 * This is a decorated version of {@link com.booktera.android.common.devMode.DevModeOn_Core}.
 * This only calls the DevMode methods if them are enabled in {@link com.booktera.android.common.Config}
 */
public class DevModeOn implements IDevMode
{
    private IDevMode _core;

    public DevModeOn(IDevMode _core)
    {
        this._core = _core;
    }

    // --

    @Override
    public void mockLogin()
    {
        if (DevMode.IsEnabled.MockLogin)
            _core.mockLogin();
    }

    @Override
    public void fillLoginForm(LoginActivity.ViewHolder vh)
    {
        if (DevMode.IsEnabled.FillLoginForm)
            _core.fillLoginForm(vh);
    }

    @Override
    public void fillRegisterForm(RegisterActivity.ViewHolder vh)
    {
        if (DevMode.IsEnabled.FillRegisterForm)
            _core.fillRegisterForm(vh);
    }
    @Override
    public void hideTitleBar(Activity activity)
    {
        if (DevMode.IsEnabled.HideTitleBar)
            _core.hideTitleBar(activity);
    }
}
