package com.booktera.androidclientproxy.lib.proxy.clients;

import com.booktera.androidclientproxy.lib.models.ProductModels.InCategoryCWPLVM;
import com.booktera.androidclientproxy.lib.proxy.Request;
import com.booktera.androidclientproxy.lib.proxy.base.RestServiceClientBase;
import com.booktera.androidclientproxy.lib.proxy.clients.interfaces.ICategoryManagerClient;
import com.booktera.androidclientproxy.lib.utils.Action_1;
import org.apache.http.HttpResponse;

public class CategoryManagerClient extends RestServiceClientBase implements ICategoryManagerClient
{
    public CategoryManagerClient()
    {
        super("http://localhost:50135/EntityManagers/CategoryManagerService.svc");
        clientClassNameLower = "categorymanager";
    }

    @Override
    public void getCategoriesWithProductsInCategory(int pageNumber, int productsPerPage, String baseCategoryFu, Action_1<InCategoryCWPLVM> todoWithResponse, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Request<InCategoryCWPLVM> request = new Request<>(InCategoryCWPLVM.class, 3);
        request.requestUrl = baseAddress + "/GetCategoriesWithProductsInCategory";
        request.todoWithResponse = todoWithResponse;
        request.todoIfResponseFailed = todoIfResponseFailed;
        request.requestData.put("pageNumber", pageNumber);
        request.requestData.put("productsPerPage", productsPerPage);
        request.requestData.put("baseCategoryFu", baseCategoryFu);

        sendRequest(request);
    }
}


