package com.booktera.android.common.models;

import com.booktera.android.common.models.base.MapCache;
import com.booktera.androidclientproxy.lib.models.ProductModels.InCategoryPLVM;

/**
 * Created by Norbert on 2015.02.09..
 */
public class ProductsInCategoryVM extends MapCache<String, InCategoryPLVM>
{
    public static ProductsInCategoryVM Instance = new ProductsInCategoryVM();

    protected ProductsInCategoryVM()
    {
    }

    // --

    public InCategoryPLVM getProductsInCategory(String categoryFU)
    {
        return getValue(categoryFU);
    }
    public void setProductsInCategory(String categoryFU, InCategoryPLVM productsInCategory)
    {
        setValue(categoryFU, productsInCategory);
    }
}
