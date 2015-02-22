package com.booktera.androidclientproxy.lib.proxy.clients.interfaces;

import com.booktera.androidclientproxy.lib.models.ProductModels.InCategoryCWPLVM;
import com.booktera.androidclientproxy.lib.utils.Action_1;
import org.apache.http.HttpResponse;

/**
 * Created by Norbert on 2015.02.22..
 */
public interface ICategoryManagerClient
{
    void getCategoriesWithProductsInCategory(int pageNumber, int productsPerPage, String baseCategoryFu, Action_1<InCategoryCWPLVM> todoWithResponse, Action_1<HttpResponse> todoIfResponseFailed);
}
