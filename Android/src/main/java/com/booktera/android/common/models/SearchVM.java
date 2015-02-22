package com.booktera.android.common.models;

import com.booktera.android.common.models.base.MapCache;
import com.booktera.androidclientproxy.lib.models.ProductModels.BookBlockPLVM;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Norbert on 2015.02.09..
 */
public class SearchVM extends MapCache<String, BookBlockPLVM>
{
    public static SearchVM Instance = new SearchVM();

    protected SearchVM()
    {
    }

    // --

    public BookBlockPLVM getSearchResults(String searchText)
    {
        return getValue(searchText);
    }
    public void setSearchResults(String searchText, BookBlockPLVM searchResults)
    {
        setValue(searchText, searchResults);
    }
}
