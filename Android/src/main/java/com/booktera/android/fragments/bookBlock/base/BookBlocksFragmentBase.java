package com.booktera.android.fragments.bookBlock.base;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import com.booktera.android.common.bookBlock.BookBlockArrayAdapter;
import com.booktera.android.fragments.base.ListViewFragmentBase;
import com.booktera.androidclientproxy.lib.models.ProductModels.BookBlockPLVM;

public abstract class BookBlocksFragmentBase extends ListViewFragmentBase
{
    /**
     * While implementing, you have to call {@link #applyData(com.booktera.androidclientproxy.lib.models.ProductModels.BookBlockPLVM)}
     * with the (asynchronously)downloaded/cached data
     */
    @Override
    protected abstract void loadData();

    protected void applyData(BookBlockPLVM data)
    {
        FragmentActivity activityForDebug = getActivity();

        getActivity().runOnUiThread(() ->
        {
            if (data.getProducts().isEmpty())
            {
                vh.noResultTextView.setVisibility(View.VISIBLE);
            }
            else
            {
                //TODO remove after found the null exception
                Log.d(tag, "getActivity(): " + getActivity());
                Log.d(tag, "activityForDebug: " + activityForDebug);

                BookBlockArrayAdapter bookBlockArrayAdapter = new BookBlockArrayAdapter(
                    getActivity().getApplicationContext(),//TODO there was here a nll reference exception once!
                    data
                );

                vh.listView.setAdapter(bookBlockArrayAdapter);
                vh.noResultTextView.setVisibility(View.GONE);
            }
        });
    }
}

