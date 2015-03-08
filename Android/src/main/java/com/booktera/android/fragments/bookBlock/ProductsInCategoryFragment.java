package com.booktera.android.fragments.bookBlock;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import com.booktera.android.R;
import com.booktera.android.common.Constants;
import com.booktera.android.common.models.ProductsInCategoryVM;
import com.booktera.android.common.utils.Utils;
import com.booktera.android.fragments.bookBlock.base.BookBlocksFragmentBase;
import com.booktera.androidclientproxy.lib.models.ProductModels.InCategoryPLVM;
import com.booktera.androidclientproxy.lib.proxy.Services;

public class ProductsInCategoryFragment extends BookBlocksFragmentBase
{
    private String categoryFriendlyUrl;

    public static ProductsInCategoryFragment newInstance(String categoryFriendlyUrl)
    {
        Bundle args = new Bundle();
        args.putString(Constants.PARAM_CATEGORY_FU, categoryFriendlyUrl);

        ProductsInCategoryFragment fragment = new ProductsInCategoryFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            categoryFriendlyUrl = getArguments().getString(Constants.PARAM_CATEGORY_FU);
    }

    @Override
    protected void loadData()
    {
        if (TextUtils.isEmpty(categoryFriendlyUrl))
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

