package com.booktera.androidclientproxy.lib.models.ProductModels;

import com.booktera.androidclientproxy.lib.models.EntityFramework.Category;

import java.util.List;

public class InCategoryCWPLVM   
{
    private Category BaseCategory;
    public Category getBaseCategory() {
        return BaseCategory;
    }

    public void setBaseCategory(Category value) {
        BaseCategory = value;
    }

    private List<InCategoryPLVM> ChildCategoriesWithProducts;
    public List<InCategoryPLVM> getChildCategoriesWithProducts() {
        return ChildCategoriesWithProducts;
    }

    public void setChildCategoriesWithProducts(List<InCategoryPLVM> value) {
        ChildCategoriesWithProducts = value;
    }

}


