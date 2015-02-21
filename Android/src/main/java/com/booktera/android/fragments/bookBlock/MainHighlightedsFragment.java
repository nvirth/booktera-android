package com.booktera.android.fragments.bookBlock;

import android.view.View;
import com.booktera.android.fragments.bookBlock.base.BookBlocksFragment;
import com.booktera.android.common.models.MainVM;
import com.booktera.androidclientproxy.lib.proxy.Services;

public class MainHighlightedsFragment extends BookBlocksFragment
{
    @Override
    protected void loadData()
    {
        if (MainVM.Instance.getMainHighlightedProducts() == null)
            Services.ProductManager.getMainHighlighteds(1, 8,
                bookBlockPLVM ->
                {
                    MainVM.Instance.setMainHighlightedProducts(bookBlockPLVM);
                    applyData(bookBlockPLVM);
                }
                , null);
        else
            applyData(MainVM.Instance.getMainHighlightedProducts());
    }
}

