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
        FragmentActivity activityForDebug = getActivity();

        getActivity().runOnUiThread(() ->
        {
            if (data.length == 0)
            {
                vh.noResultTextView.setVisibility(View.VISIBLE);
            }
            else
            {
                //TODO remove after found the null exception
                Log.d(tag, "getActivity(): " + getActivity());
                Log.d(tag, "activityForDebug: " + activityForDebug);

                UserOrderArrayAdapter bookBlockArrayAdapter = new UserOrderArrayAdapter(
                    getActivity(),//TODO there was here a nll reference exception once!
                    data
                );

                vh.listView.setAdapter(bookBlockArrayAdapter);
                vh.noResultTextView.setVisibility(View.GONE);
            }
        });
    }
}

