package com.booktera.android.fragments.userOrder.base;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import com.booktera.android.common.userOrder.UserOrderArrayAdapter;
import com.booktera.android.fragments.base.ListViewFragmentBase;
import com.booktera.androidclientproxy.lib.models.UserOrderPLVM;

public abstract class UserOrderFragmentBase extends ListViewFragmentBase
{
    /**
     * While implementing, you have to call {@link #applyData(UserOrderPLVM[])}
     * with the (asynchronously)downloaded/cached data
     */
    @Override
    protected abstract void loadData();

    protected void applyData(UserOrderPLVM[] data)
    {
        runOnUiThread(activity ->
        {
            if (data.length == 0)
            {
                vh.noResultTextView.setVisibility(View.VISIBLE);
            }
            else
            {
                UserOrderArrayAdapter bookBlockArrayAdapter = new UserOrderArrayAdapter(
                    activity,
                    data
                );

                vh.listView.setAdapter(bookBlockArrayAdapter);
                vh.noResultTextView.setVisibility(View.GONE);
            }
        });
    }
}

