package com.booktera.android.activities.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import com.booktera.android.common.Constants;
import com.booktera.android.common.utils.Utils;
import com.booktera.androidclientproxy.lib.utils.Action_1;

public abstract class BaseActivity extends FragmentActivity
{
    protected final String tag = ((Object) this).getClass().toString();

    /**
     * Shows a Toast with length: Toast.LENGTH_SHORT
     */
    protected void showToast(String msg)
    {
        Utils.showToast(msg);
    }
    protected void showToast(String msg, boolean isLong)
    {
        Utils.showToast(msg, isLong);
    }

    protected void runInFragmentTransaction(Action_1<FragmentTransaction> action)
    {
        Utils.runInFragmentTransaction(this, action);
    }

    /**
     * Extracts the given parameter from the 'getIntent().getExtras()' Bundle. Throws error if it/the bundle is null.
     */
    protected String extractParam(String paramName)
    {
        String errorMsg = "You can't call this activity without passing this parameter: " + paramName;
        Bundle bundle = getIntent().getExtras();

        return Utils.extractParam(bundle, paramName, tag, errorMsg);
    }

    protected void alert(String title, String message)
    {
        Utils.alert(this, title, message);
    }
}
