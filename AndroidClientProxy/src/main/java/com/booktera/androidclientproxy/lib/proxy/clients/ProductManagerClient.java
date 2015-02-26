package com.booktera.androidclientproxy.lib.proxy.clients;

import com.booktera.androidclientproxy.lib.models.ProductModels.BookBlockPLVM;
import com.booktera.androidclientproxy.lib.models.ProductModels.InCategoryPLVM;
import com.booktera.androidclientproxy.lib.proxy.Request;
import com.booktera.androidclientproxy.lib.proxy.base.RestServiceClientBase;
import com.booktera.androidclientproxy.lib.proxy.clients.interfaces.IProductManagerClient;
import com.booktera.androidclientproxy.lib.utils.Action_1;
import com.booktera.androidclientproxy.lib.utils.Action_2;
import org.apache.http.HttpResponse;

public class ProductManagerClient extends RestServiceClientBase implements IProductManagerClient
{
    public ProductManagerClient()
    {
        super("http://localhost:50135/EntityManagers/ProductManagerService.svc");
        clientClassNameLower = "productmanager";
    }

    @Override
    public void getProductsInCategory(String categoryFriendlyUrl, int pageNumber, int productsPerPage, Action_1<InCategoryPLVM> todoWithResponse, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Request<InCategoryPLVM> request = new Request<>(InCategoryPLVM.class, 3);
        request.requestUrl = baseAddress + "/GetProductsInCategory";
        request.todoWithResponse = todoWithResponse;
        request.todoIfResponseFailed = todoIfResponseFailed;
        request.requestData.put("categoryFriendlyUrl", categoryFriendlyUrl);
        request.requestData.put("pageNumber", pageNumber);
        request.requestData.put("productsPerPage", productsPerPage);

        sendRequest(request);
    }

    @Override
    public void getMainHighlighteds(int pageNumber, int productsPerPage, Action_1<BookBlockPLVM> todoWithResponse, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Request<BookBlockPLVM> request = new Request<>(BookBlockPLVM.class, 2);
        request.requestUrl = baseAddress + "/GetMainHighlighteds";
        request.todoWithResponse = todoWithResponse;
        request.todoIfResponseFailed = todoIfResponseFailed;
        request.requestData.put("pageNumber", pageNumber);
        request.requestData.put("productsPerPage", productsPerPage);

        sendRequest(request);
    }

    @Override
    public void getNewests(int pageNumber, int productsPerPage, int numOfProducts, Action_1<BookBlockPLVM> todoWithResponse, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Request<BookBlockPLVM> request = new Request<>(BookBlockPLVM.class, 3);
        request.requestUrl = baseAddress + "/GetNewests";
        request.todoWithResponse = todoWithResponse;
        request.todoIfResponseFailed = todoIfResponseFailed;
        request.requestData.put("pageNumber", pageNumber);
        request.requestData.put("productsPerPage", productsPerPage);
        request.requestData.put("numOfProducts", numOfProducts);

        sendRequest(request);
    }

    @Override
    public void getUsersProductsByFriendlyUrl(String friendlyUrl, int pageNumber, int productsPerPage, Action_2<BookBlockPLVM, String> todoWithResponse, Action_1<HttpResponse> todoIfResponseFailed)
    {
        getUsersProductsByFriendlyUrl(friendlyUrl, pageNumber, productsPerPage, false, todoWithResponse, todoIfResponseFailed);
    }
    @Override
    public void getUsersProductsByFriendlyUrl(String friendlyUrl, int pageNumber, int productsPerPage, boolean forExchange, Action_2<BookBlockPLVM, String> todoWithResponse, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Request<GetUsersProductsByFriendlyUrlResult> request = new Request<>(GetUsersProductsByFriendlyUrlResult.class, 4);
        request.requestUrl = baseAddress + "/GetUsersProductsByFriendlyUrl";
        request.requestData.put("friendlyUrl", friendlyUrl);
        request.requestData.put("pageNumber", pageNumber);
        request.requestData.put("productsPerPage", productsPerPage);
        request.requestData.put("forExchange", forExchange);
        request.todoIfResponseFailed = todoIfResponseFailed;
        request.todoWithResponse = res -> {
            if (todoWithResponse != null)
                todoWithResponse.run(res.GetUsersProductsByFriendlyUrlResult, res.userName);
        };
        sendRequest(request);
    }

    static class GetUsersProductsByFriendlyUrlResult
    {
        BookBlockPLVM GetUsersProductsByFriendlyUrlResult;
        String userName;
    }

    @Override
    public BookBlockPLVM getUsersProducts(int userID, int pageNumber, int productsPerPage, boolean forExchange)
    {
        throw new UnsupportedOperationException();
    }

}


