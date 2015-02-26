package com.booktera.android.activities.base;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import com.booktera.android.common.utils.Utils;
import com.booktera.androidclientproxy.lib.utils.Action_1;

public class BaseActivity extends FragmentActivity
{
    protected final String tag = this.getClass().toString();

    /**
     * Shows a Toast with length: Toast.LENGTH_SHORT
     */
    public void showToast(String msg)
    {
        Utils.showToast(msg);
    }
    public void showToast(String msg, boolean isLong)
    {
        Utils.showToast(msg, isLong);
    }

    public void runInFragmentTransaction(Action_1<FragmentTransaction> action)
    {
        Utils.runInFragmentTransaction(this, action);
    }

}
