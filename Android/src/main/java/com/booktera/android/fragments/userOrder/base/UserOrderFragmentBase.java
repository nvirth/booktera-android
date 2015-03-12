package com.booktera.android.fragments.userOrder.base;

import android.view.View;
import com.booktera.android.common.userOrder.UserOrderArrayAdapter;
import com.booktera.android.fragments.base.ListViewFragmentBase;
import com.booktera.androidclientproxy.lib.models.UserOrderPLVM;

import java.util.List;

public abstract class UserOrderFragmentBase extends ListViewFragmentBase
{
    /**
     * While implementing, you have to call {@link #applyData(UserOrderPLVM[])}
     * with the (asynchronously)downloaded/cached data
     */
    @Override
    protected abstract void loadData();

    protected void applyData(List<UserOrderPLVM> data)
    {
        runOnUiThread(activity ->
        {
            if (data.isEmpty())
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

