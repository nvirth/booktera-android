package com.booktera.androidclientproxy.lib.proxy.clients;

import com.booktera.androidclientproxy.lib.models.ProductModels.BookBlockPLVM;
import com.booktera.androidclientproxy.lib.models.ProductModels.BookRowPLVM;
import com.booktera.androidclientproxy.lib.models.ProductModels.InCategoryPLVM;
import com.booktera.androidclientproxy.lib.proxy.Request;
import com.booktera.androidclientproxy.lib.proxy.base.RestServiceClientBase;
import com.booktera.androidclientproxy.lib.proxy.clients.interfaces.IProductGroupManagerClient;
import com.booktera.androidclientproxy.lib.proxy.clients.interfaces.IProductManagerClient;
import com.booktera.androidclientproxy.lib.utils.Action_1;
import org.apache.http.HttpResponse;

public class ProductGroupManagerClient extends RestServiceClientBase implements IProductGroupManagerClient
{
    public ProductGroupManagerClient()
    {
        super("http://localhost:50135/EntityManagers/ProductGroupManagerService.svc");
        clientClassNameLower = "productgroupmanager";
    }

    @Override
    public void search(String searchText, int pageNumber, int productsPerPage, boolean needCategory, Action_1<BookBlockPLVM> todoWithResponse, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Request<BookBlockPLVM> request = new Request<>(BookBlockPLVM.class, 4);
        request.requestUrl = baseAddress + "/Search";
        request.todoWithResponse = todoWithResponse;
        request.todoIfResponseFailed = todoIfResponseFailed;
        request.requestData.put("searchText", searchText);
        request.requestData.put("pageNumber", pageNumber);
        request.requestData.put("productsPerPage", productsPerPage);
        request.requestData.put("needCategory", needCategory);

        sendRequest(request);
    }

    @Override
    public void getFullDetailed(String friendlyUrl, int pageNumber, int productsPerPage, Action_1<BookRowPLVM> todoWithResponse, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Request<BookRowPLVM> request = new Request<>(BookRowPLVM.class, 3);
        request.requestUrl = baseAddress + "/GetFullDetailed";
        request.todoWithResponse = todoWithResponse;
        request.todoIfResponseFailed = todoIfResponseFailed;
        request.requestData.put("friendlyUrl", friendlyUrl);
        request.requestData.put("pageNumber", pageNumber);
        request.requestData.put("productsPerPage", productsPerPage);

        sendRequest(request);
    }
}


