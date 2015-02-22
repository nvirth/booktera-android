package com.booktera.android.fragments.bookBlock;

import android.os.Bundle;
import android.view.View;
import com.booktera.android.R;
import com.booktera.android.common.models.ProductsInCategoryVM;
import com.booktera.android.common.models.SearchVM;
import com.booktera.android.common.utils.Utils;
import com.booktera.android.fragments.bookBlock.base.BookBlocksFragment;
import com.booktera.androidclientproxy.lib.models.ProductModels.BookBlockPLVM;
import com.booktera.androidclientproxy.lib.models.ProductModels.InCategoryPLVM;
import com.booktera.androidclientproxy.lib.proxy.Services;

public class ProductsInCategoryFragment extends BookBlocksFragment
{
    public static final String CATEGORY_FRIENDLY_URL = "CATEGORY_FRIENDLY_URL";
    private String categoryFriendlyUrl;

    public static ProductsInCategoryFragment newInstance(String categoryFriendlyUrl)
    {
        Bundle args = new Bundle();
        args.putString(CATEGORY_FRIENDLY_URL, categoryFriendlyUrl);

        ProductsInCategoryFragment fragment = new ProductsInCategoryFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            categoryFriendlyUrl = getArguments().getString(CATEGORY_FRIENDLY_URL);
    }

    @Override
    protected void loadData()
    {
        if (Utils.isNullOrEmpty(categoryFriendlyUrl))
        {
            vh.noResultTextView.setText(getActivity().getString(R.string._choose_a_category_));
            vh.noResultTextView.setVisibility(View.VISIBLE);
            return;
        }

        InCategoryPLVM cached = ProductsInCategoryVM.Instance.getProductsInCategory(categoryFriendlyUrl);
        if (cached != null)
            applyData(cached);
        else
            Services.ProductManager.getProductsInCategory(categoryFriendlyUrl, 1, 100,
                inCategoryPLVM ->
                {
                    ProductsInCategoryVM.Instance.setProductsInCategory(categoryFriendlyUrl, inCategoryPLVM);
                    applyData(inCategoryPLVM);
                }, null);
    }
}

