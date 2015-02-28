package com.booktera.androidclientproxy.lib.proxy.clients.interfaces;

import com.booktera.androidclientproxy.lib.models.ProductModels.BookBlockPLVM;
import com.booktera.androidclientproxy.lib.models.ProductModels.InCategoryPLVM;
import com.booktera.androidclientproxy.lib.utils.Action_1;
import com.booktera.androidclientproxy.lib.utils.Action_2;
import org.apache.http.HttpResponse;

/**
 * Created by Norbert on 2015.02.01..
 */
public interface IProductManagerClient
{
    void getProductsInCategory(String categoryFriendlyUrl, int pageNumber, int productsPerPage, Action_1<InCategoryPLVM> todoWithResponse, Action_1<HttpResponse> todoIfResponseFailed);
    void getMainHighlighteds(int pageNumber, int productsPerPage, Action_1<BookBlockPLVM> todoWithResponse, Action_1<HttpResponse> todoIfResponseFailed);
    void getNewests(int pageNumber, int productsPerPage, int numOfProducts, Action_1<BookBlockPLVM> todoWithResponse, Action_1<HttpResponse> todoIfResponseFailed);
    BookBlockPLVM getUsersProducts(int userID, int pageNumber, int productsPerPage, boolean forExchange);

    /**
     * @param todoWithResponse Action_2[BookBlockPLVM: usersProducts, String: userName]
     */
    void getUsersProductsByFriendlyUrl(String friendlyUrl, int pageNumber, int productsPerPage, boolean forExchange, Action_2<BookBlockPLVM, String> todoWithResponse, Action_1<HttpResponse> todoIfResponseFailed);

    /**
     * @param todoWithResponse Action_2[BookBlockPLVM: usersProducts, String: userName]
     */
    void getUsersProductsByFriendlyUrl(String friendlyUrl, int pageNumber, int productsPerPage, Action_2<BookBlockPLVM, String> todoWithResponse, Action_1<HttpResponse> todoIfResponseFailed);
}
