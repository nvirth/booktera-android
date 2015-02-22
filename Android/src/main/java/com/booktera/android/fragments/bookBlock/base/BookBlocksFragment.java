package com.booktera.android.fragments.bookBlock.base;

import android.view.View;
import com.booktera.android.common.bookBlock.BookBlockArrayAdapter;
import com.booktera.androidclientproxy.lib.models.ProductModels.BookBlockPLVM;

public abstract class BookBlocksFragment extends ListViewFragmentBase
{
    private static final String tag = BookBlocksFragment.class.toString();

    /**
     * While implementing, you have to call {@link #applyData(com.booktera.androidclientproxy.lib.models.ProductModels.BookBlockPLVM)}
     * with the (asynchronously)downloaded/cached data
     */
    @Override
    protected abstract void loadData();

    protected void applyData(BookBlockPLVM data)
    {
        getActivity().runOnUiThread(() ->
        {
            if (data.getProducts().isEmpty())
            {
                vh.noResultTextView.setVisibility(View.VISIBLE);
            }
            else
            {
                BookBlockArrayAdapter bookBlockArrayAdapter = new BookBlockArrayAdapter(
                    getActivity().getApplicationContext(),
                    data
                );

                vh.listView.setAdapter(bookBlockArrayAdapter);
                vh.noResultTextView.setVisibility(View.GONE);
            }
        });
    }
}

