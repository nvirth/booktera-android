package com.booktera.android.fragments.bookBlock;

import android.os.Bundle;
import com.booktera.android.common.Constants;
import com.booktera.android.common.models.SearchVM;
import com.booktera.android.fragments.bookBlock.base.BookBlocksFragment;
import com.booktera.androidclientproxy.lib.models.ProductModels.BookBlockPLVM;
import com.booktera.androidclientproxy.lib.proxy.Services;

public class SearchFragment extends BookBlocksFragment
{
    private String searchText;

    public static SearchFragment newInstance(String searchInput)
    {
        Bundle args = new Bundle();
        args.putString(Constants.PARAM_SEARCH_INPUT, searchInput);

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            searchText = getArguments().getString(Constants.PARAM_SEARCH_INPUT);
    }

    @Override
    protected void loadData()
    {
        if (searchText == null || searchText.length() < 3)
            return;

        BookBlockPLVM cached = SearchVM.Instance.getSearchResults(searchText);
        if (cached != null)
            applyData(cached);
        else
            Services.ProductGroupManager.search(searchText, 1, 100, /*needCategory*/ false,
                bookBlockPLVM ->
                {
                    SearchVM.Instance.setSearchResults(searchText, bookBlockPLVM);
                    applyData(bookBlockPLVM);
                }
                , null);
    }
}

