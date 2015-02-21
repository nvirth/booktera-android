package com.booktera.android.common.models;

import com.booktera.androidclientproxy.lib.models.ProductModels.BookBlockPLVM;

/**
 * Created by Norbert on 2015.02.09..
 */
public class SearchVM
{
    public static SearchVM Instance = new SearchVM();

    protected SearchVM()
    {
    }

    // --

    private BookBlockPLVM searchResults;
    private String searchText = "";

    public BookBlockPLVM getSearchResults()
    {
        return searchResults;
    }
    public void setSearchResults(BookBlockPLVM searchResults)
    {
        this.searchResults = searchResults;
    }
    public String getSearchText()
    {
        return searchText;
    }
    public void setSearchText(String searchText)
    {
        this.searchText = searchText;
    }
}
