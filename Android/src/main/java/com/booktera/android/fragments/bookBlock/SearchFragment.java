package com.booktera.android.fragments.bookBlock;

import android.os.Bundle;
import com.booktera.android.common.models.SearchVM;
import com.booktera.android.fragments.bookBlock.base.BookBlocksFragment;
import com.booktera.androidclientproxy.lib.proxy.Services;

public class SearchFragment extends BookBlocksFragment
{
    public static final String PARAM_SEARCH_INPUT = "PARAM_SEARCH_INPUT";
    private String searchText;

    public static SearchFragment newInstance(String searchInput)
    {
        Bundle args = new Bundle();
        args.putString(PARAM_SEARCH_INPUT, searchInput);

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            searchText = getArguments().getString(PARAM_SEARCH_INPUT);
    }

    @Override
    protected void loadData()
    {
        if (searchText == null || searchText.length() < 3)
            return;

        if (SearchVM.Instance.getSearchText().equals(searchText))
            applyData(SearchVM.Instance.getSearchResults());
        else
            Services.ProductGroupManager.search(searchText, 1, 100, /*needCategory*/ false,
                bookBlockPLVM ->
                {
                    SearchVM.Instance.setSearchResults(bookBlockPLVM);
                    SearchVM.Instance.setSearchText(searchText);
                    applyData(bookBlockPLVM);
                }
                , null);
    }
}

