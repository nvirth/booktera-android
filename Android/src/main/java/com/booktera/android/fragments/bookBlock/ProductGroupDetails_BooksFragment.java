package com.booktera.android.fragments.bookBlock;

import android.os.Bundle;
import com.booktera.android.common.Constants;
import com.booktera.android.common.models.ProductGroupDetailsVM;
import com.booktera.android.fragments.bookBlock.base.BookBlocksFragmentBase;
import com.booktera.androidclientproxy.lib.models.ProductModels.BookBlockPLVM;

public class ProductGroupDetails_BooksFragment extends BookBlocksFragmentBase
{
    private String productGroupFU;

    public static ProductGroupDetails_BooksFragment newInstance(String productGroupFU)
    {
        Bundle args = new Bundle();
        args.putString(Constants.PARAM_PRODUCT_GROUP_FU, productGroupFU);

        ProductGroupDetails_BooksFragment fragment = new ProductGroupDetails_BooksFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        productGroupFU = extractStringParam(Constants.PARAM_PRODUCT_GROUP_FU);
    }

    @Override
    protected void loadData()
    {
        BookBlockPLVM books = ProductGroupDetailsVM.Instance.getBooks(productGroupFU);
        applyData(books);
    }
}

