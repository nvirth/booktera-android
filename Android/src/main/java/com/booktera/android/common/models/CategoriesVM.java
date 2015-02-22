package com.booktera.android.common.models;

import com.booktera.android.common.models.base.MapCache;
import com.booktera.androidclientproxy.lib.models.ProductModels.InCategoryCWPLVM;

/**
 * Created by Norbert on 2015.02.09..
 */
public class CategoriesVM extends MapCache<String, InCategoryCWPLVM>
{
    public static CategoriesVM Instance = new CategoriesVM();

    protected CategoriesVM()
    {
    }

    // --

    public InCategoryCWPLVM getCategories(String categoryFU)
    {
        return getValue(categoryFU);
    }
    public void setCategories(String categoryFU, InCategoryCWPLVM categories)
    {
        setValue(categoryFU, categories);
    }
}
