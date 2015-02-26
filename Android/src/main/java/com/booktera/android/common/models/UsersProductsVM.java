package com.booktera.android.common.models;

import com.booktera.android.common.models.base.MapCache;
import com.booktera.androidclientproxy.lib.models.ProductModels.BookBlockPLVM;

/**
 * Created by Norbert on 2015.02.09..
 */
public class UsersProductsVM extends MapCache<String, BookBlockPLVM>
{
    public static UsersProductsVM Instance = new UsersProductsVM();

    protected UsersProductsVM()
    {
    }

    // --

    public BookBlockPLVM getUsersProducts(String userFU)
    {
        return getValue(userFU);
    }
    public void setUsersProducts(String userFU, BookBlockPLVM usersProducts)
    {
        setValue(userFU, usersProducts);
    }
}
