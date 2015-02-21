package com.booktera.androidclientproxy.lib.models.ProductModels;

import com.booktera.androidclientproxy.lib.enums.BookBlockType;
import com.booktera.androidclientproxy.lib.models.Paging;

import java.util.List;

public class BookBlockPLVM   
{
    private BookBlockType BookBlockType;
    public BookBlockType getBookBlockType() {
        return BookBlockType;
    }

    public void setBookBlockType(BookBlockType value) {
        BookBlockType = value;
    }

    private List<InBookBlockPVM> Products;
    public List<InBookBlockPVM> getProducts() {
        return Products;
    }

    public void setProducts(List<InBookBlockPVM> value) {
        Products = value;
    }

    private Paging Paging;
    public Paging getPaging() {
        return Paging;
    }

    public void setPaging(Paging value) {
        Paging = value;
    }

}


