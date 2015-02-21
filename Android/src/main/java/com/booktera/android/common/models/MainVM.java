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

    private BookBlockPLVM MainHighlightedProducts;
    private BookBlockPLVM NewestProducts;

    public BookBlockPLVM getMainHighlightedProducts()
    {
        return MainHighlightedProducts;
    }
    public void setMainHighlightedProducts(BookBlockPLVM mainHighlightedProducts)
    {
        MainHighlightedProducts = mainHighlightedProducts;
    }
    public BookBlockPLVM getNewestProducts()
    {
        return NewestProducts;
    }
    public void setNewestProducts(BookBlockPLVM newestProducts)
    {
        NewestProducts = newestProducts;
    }
}
