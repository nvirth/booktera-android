package com.booktera.android.activities.base;

import android.view.ViewGroup;
import com.booktera.android.R;
import com.booktera.android.common.UserData;
import com.booktera.android.common.utils.Utils;
import com.booktera.androidclientproxy.lib.proxy.Services;

public abstract class AuthenticationActivityBase extends UnAuthorizedActivity
{
    protected void doLogin(String userName, String password, ViewGroup root)
    {
        if (Utils.isNullOrEmpty(userName) || Utils.isNullOrEmpty(password))
        {
            alert(getString(R.string.Error_), getString(R.string.UserName_and_or_password_mustn_t_be_empty));
            return;
        }

        Utils.disableOrEnableControls(root,/*enable*/ false);

        Services.Authentication.login(userName, password,/*persistent*/ false,
            (wasSuccessful, userId) -> runOnUiThread(() -> {
                if (wasSuccessful)
                {
                    UserData.Instace.setAuthenticated(true);
                    UserData.Instace.setUserName(userName);
                    UserData.Instace.setUserId(userId);

                    showToast(getString(R.string.Successful_login));

                    finish();
                }
                else
                {
                    alert(getString(R.string.Error_), getString(R.string.Wrong_userName_and_or_password));
                    Utils.disableOrEnableControls(root,/*enable*/ true);
                }
            }));
    }
}
