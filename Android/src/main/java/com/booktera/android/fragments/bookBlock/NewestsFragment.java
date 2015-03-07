package com.booktera.android.fragments.bookBlock;

import com.booktera.android.fragments.bookBlock.base.BookBlocksFragmentBase;
import com.booktera.android.common.models.MainVM;
import com.booktera.androidclientproxy.lib.proxy.Services;

public class NewestsFragment extends BookBlocksFragmentBase
{
    @Override
    protected void loadData()
    {
        if (MainVM.Instance.getNewestProducts() == null)
            Services.ProductManager.getNewests(2, 8, 16,
                bookBlockPLVM ->
                {
                    MainVM.Instance.setNewestProducts(bookBlockPLVM);
                    applyData(bookBlockPLVM);
                }
                , null);
        else
            applyData(MainVM.Instance.getNewestProducts());
    }
}

