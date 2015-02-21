package com.booktera.android.activities.base;

import android.support.v4.app.FragmentActivity;
import com.booktera.android.common.utils.Utils;

public class BaseActivity extends FragmentActivity
{
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
}
