package com.booktera.android.fragments.bookBlock;

import android.os.Bundle;
import com.booktera.android.R;
import com.booktera.android.common.Constants;
import com.booktera.android.common.models.SearchVM;
import com.booktera.android.common.models.UsersProductsVM;
import com.booktera.android.common.utils.Utils;
import com.booktera.android.fragments.bookBlock.base.BookBlocksFragment;
import com.booktera.androidclientproxy.lib.models.ProductModels.BookBlockPLVM;
import com.booktera.androidclientproxy.lib.models.ProductModels.InBookBlockPVM;
import com.booktera.androidclientproxy.lib.proxy.Services;

public class UsersProductsFragment extends BookBlocksFragment
{
    private String userFU;

    public static UsersProductsFragment newInstance(String userFU)
    {
        Bundle args = new Bundle();
        args.putString(Constants.PARAM_USER_FU, userFU);

        UsersProductsFragment fragment = new UsersProductsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        userFU = extractParam(Constants.PARAM_USER_FU);
    }

    @Override
    protected void loadData()
    {
        BookBlockPLVM cached = UsersProductsVM.Instance.getUsersProducts(userFU);
        if (cached != null)
            applyData(cached);
        else
            Services.ProductManager.getUsersProductsByFriendlyUrl(userFU, 1, 100,
                (bookBlockPLVM, userName) ->
                {
                    // Clear the UserName like properties, not to show them in ContextMenu
                    for (int i = 0; i < bookBlockPLVM.getProducts().size(); i++)
                    {
                        InBookBlockPVM inBookBlockPVM = bookBlockPLVM.getProducts().get(i);
                        inBookBlockPVM.getProduct().setUserFriendlyUrl(null);
                        inBookBlockPVM.getProduct().setUserName(null);
                    }

                    UsersProductsVM.Instance.setUsersProducts(userFU, bookBlockPLVM);
                    applyData(bookBlockPLVM);

                    // Set Title
                    getActivity().runOnUiThread(() ->
                        getActivity().setTitle(userName + getActivity().getString(R.string._someones_books)));
                }
                , null);
    }
}

