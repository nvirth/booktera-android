package com.booktera.androidclientproxy.lib.models;

public class Paging
{
    private int NumberOfPages;
    public int getNumberOfPages()
    {
        return NumberOfPages;
    }

    public void setNumberOfPages(int value)
    {
        NumberOfPages = value;
    }

    private int NumberOfProducts;
    public int getNumberOfProducts()
    {
        return NumberOfProducts;
    }

    public void setNumberOfProducts(int value)
    {
        NumberOfProducts = value;
    }

    private int ActualPageNumber;
    public int getActualPageNumber()
    {
        return ActualPageNumber;
    }

    public void setActualPageNumber(int value)
    {
        ActualPageNumber = value;
    }

    private int ProductsPerPage;
    public int getProductsPerPage()
    {
        return ProductsPerPage;
    }

    public void setProductsPerPage(int value)
    {
        ProductsPerPage = value;
    }

}


