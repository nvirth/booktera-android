package com.booktera.android.activities.base;

import android.text.TextUtils;
import android.view.ViewGroup;
import com.booktera.android.R;
import com.booktera.android.common.UserData;
import com.booktera.android.common.utils.Utils;
import com.booktera.androidclientproxy.lib.proxy.Services;

/**
 * This class only holds some common logic for Activities dealing with authentication <br />
 * <br />
 * Type hierarchy:
 <pre>
 BaseActivity
    ActionBarActivity
        UnAuthorizedActivity
            AuthenticationActivityBase
 </pre>
 */
public abstract class AuthenticationActivityBase extends UnAuthorizedActivity
{
    protected void doLogin(String userName, String password, ViewGroup root)
    {
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password))
        {
            alert(getString(R.string.Error_), getString(R.string.UserName_and_or_password_mustn_t_be_empty));
            return;
        }

        Utils.disableControls(root);

        Services.Authentication.login(userName, password,/*persistent*/ false,
            (wasSuccessful, userId) -> runOnUiThread(() -> {
                if (wasSuccessful)
                {
                    UserData.Instace.logIn(userName, userId);

                    showToast(getString(R.string.Successful_login));

                    finish();
                }
                else
                {
                    alert(getString(R.string.Error_), getString(R.string.Wrong_userName_and_or_password));
                    Utils.enableControls(root);
                }
            }));
    }
}
