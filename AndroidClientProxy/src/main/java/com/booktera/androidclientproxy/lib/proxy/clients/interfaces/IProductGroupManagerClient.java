package com.booktera.androidclientproxy.lib.proxy.clients.interfaces;

import com.booktera.androidclientproxy.lib.models.ProductModels.BookBlockPLVM;
import com.booktera.androidclientproxy.lib.models.ProductModels.BookRowPLVM;
import com.booktera.androidclientproxy.lib.models.ProductModels.InCategoryPLVM;
import com.booktera.androidclientproxy.lib.utils.Action_1;
import org.apache.http.HttpResponse;

/**
 * Created by Norbert on 2015.02.01..
 */
public interface IProductGroupManagerClient
{
    void search(String searchText, int pageNumber, int productsPerPage, boolean needCategory, Action_1<BookBlockPLVM> todoWithResponse, Action_1<HttpResponse> todoIfResponseFailed);
    void getFullDetailed(String friendlyUrl, int pageNumber, int productsPerPage, Action_1<BookRowPLVM> todoWithResponse, Action_1<HttpResponse> todoIfResponseFailed);
}
