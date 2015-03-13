package com.booktera.android.fragments.userOrder.base;

import android.os.Bundle;
import android.view.View;
import com.booktera.android.common.Constants;
import com.booktera.android.common.userOrder.UserOrderArrayAdapter;
import com.booktera.android.fragments.base.ListViewFragmentBase;
import com.booktera.androidclientproxy.lib.enums.TransactionType;
import com.booktera.androidclientproxy.lib.models.UserOrderPLVM;

import java.util.List;

public abstract class UserOrderFragmentBase extends ListViewFragmentBase
{
    protected TransactionType transactionType;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        transactionType = (TransactionType) extractSerializableParam(Constants.PARAM_TRANSACTION_TYPE);
    }

    /**
     * While implementing, you have to call {@link #applyData(List)}
     * with the (asynchronously)downloaded/cached data
     */
    @Override
    protected abstract void loadData();

    protected void applyData(List<UserOrderPLVM> data)
    {
        runOnUiThread(activity ->
        {
            UserOrderArrayAdapter bookBlockArrayAdapter = new UserOrderArrayAdapter(
                activity, data, transactionType
            );
            bookBlockArrayAdapter.setOnDataSetChangedListener(
                () -> showEmptyLabelIf(bookBlockArrayAdapter)
            );

            vh.listView.setAdapter(bookBlockArrayAdapter);
            showEmptyLabelIf(bookBlockArrayAdapter);
        });
    }
    private void showEmptyLabelIf(UserOrderArrayAdapter bookBlockArrayAdapter)
    {
        if (bookBlockArrayAdapter.isEmpty())
            vh.noResultTextView.setVisibility(View.VISIBLE);
        else
            vh.noResultTextView.setVisibility(View.GONE);
    }
}

