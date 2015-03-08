package com.booktera.android.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import com.booktera.android.R;
import com.booktera.android.activities.CategoryActivity;
import com.booktera.android.common.Constants;
import com.booktera.android.common.models.CategoriesVM;
import com.booktera.android.common.utils.Utils;
import com.booktera.android.fragments.base.ListViewFragmentBase;
import com.booktera.androidclientproxy.lib.models.ProductModels.InCategoryCWPLVM;
import com.booktera.androidclientproxy.lib.models.ProductModels.InCategoryPLVM;
import com.booktera.androidclientproxy.lib.proxy.Services;

import java.util.ArrayList;

public class CategoriesFragment extends ListViewFragmentBase
{
    private String categoryFriendlyUrl;

    public static CategoriesFragment newInstance(String categoryFriendlyUrl)
    {
        Bundle args = new Bundle();
        args.putString(Constants.PARAM_CATEGORY_FU, categoryFriendlyUrl);

        CategoriesFragment fragment = new CategoriesFragment();
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
        InCategoryCWPLVM cached = CategoriesVM.Instance.getCategories(categoryFriendlyUrl);
        if (cached != null)
            applyData(cached);
        else
            Services.CategoryManager.getCategoriesWithProductsInCategory(1, 0, categoryFriendlyUrl,
                inCategoryCWPLVM ->
                {
                    CategoriesVM.Instance.setCategories(categoryFriendlyUrl, inCategoryCWPLVM);
                    applyData(inCategoryCWPLVM);
                }
                , null);
    }

    private void applyData(InCategoryCWPLVM dataIn)
    {
        runOnUiThread(activity ->
        {
            InCategoryCWPLVM data = dataIn; // To be able to debug within a lambda

            // -- Set Title
            if (data.getBaseCategory() == null || TextUtils.isEmpty(data.getBaseCategory().getDisplayName()))
                activity.setTitle(activity.getString(R.string.app_name));
            else
                activity.setTitle(data.getBaseCategory().getFullPath());

            // -- Fetch data
            if (data.getChildCategoriesWithProducts().size() == 1) // Leaf category
            {
                vh.noResultTextView.setVisibility(View.VISIBLE);
            }
            else
            {
                vh.noResultTextView.setVisibility(View.GONE);

                ArrayList<String> categoryNames = new ArrayList<>();
                for (InCategoryPLVM inCategoryPLVM : data.getChildCategoriesWithProducts())
                    categoryNames.add(inCategoryPLVM.getCategory().getDisplayName());

                ArrayAdapter bookBlockArrayAdapter = new ArrayAdapter<>(
                    activity.getApplicationContext(),
                    R.layout.row_simple_list_item,
                    categoryNames
                );

                vh.listView.setAdapter(bookBlockArrayAdapter);

                vh.listView.setOnItemClickListener(
                    (parent, view, position, id) ->
                    {
                        InCategoryPLVM inCategoryPLVM = data.getChildCategoriesWithProducts().get(position);
                        String categoryFU = inCategoryPLVM.getCategory().getFriendlyUrl();

                        Intent gotoSubcategoryIntent = new Intent(activity, CategoryActivity.class);
                        gotoSubcategoryIntent.putExtra(Constants.PARAM_CATEGORY_FU, categoryFU);

                        startActivity(gotoSubcategoryIntent);
                    });
            }
        });
    }

}

