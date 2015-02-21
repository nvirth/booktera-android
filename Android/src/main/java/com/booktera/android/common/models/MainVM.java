package com.booktera.android.common.models;

import com.booktera.androidclientproxy.lib.models.ProductModels.BookBlockPLVM;

/**
 * Created by Norbert on 2015.02.09..
 */
public class MainVM
{
    public static MainVM Instance = new MainVM();

    protected MainVM()
    {
    }

    // --

    private BookBlockPLVM mainHighlightedProducts;
    private BookBlockPLVM newestProducts;

    public BookBlockPLVM getMainHighlightedProducts()
    {
        return mainHighlightedProducts;
    }
    public void setMainHighlightedProducts(BookBlockPLVM mainHighlightedProducts)
    {
        this.mainHighlightedProducts = mainHighlightedProducts;
    }
    public BookBlockPLVM getNewestProducts()
    {
        return newestProducts;
    }
    public void setNewestProducts(BookBlockPLVM newestProducts)
    {
        this.newestProducts = newestProducts;
    }
}
