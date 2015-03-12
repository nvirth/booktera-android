package com.booktera.androidclientproxy.lib.proxy.clients;

import com.booktera.androidclientproxy.lib.enums.HttpPostVerb;
import com.booktera.androidclientproxy.lib.models.UserOrderPLVM;
import com.booktera.androidclientproxy.lib.proxy.clients.interfaces.ITransactionManagerClient;
import com.booktera.androidclientproxy.lib.utils.GsonHelper;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpResponse;
import com.booktera.androidclientproxy.lib.utils.Action;
import com.booktera.androidclientproxy.lib.utils.Action_1;
import com.booktera.androidclientproxy.lib.proxy.Request;
import com.booktera.androidclientproxy.lib.proxy.base.RestServiceClientBase;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TransactionManagerClient extends RestServiceClientBase implements ITransactionManagerClient
{
    public TransactionManagerClient()
    {
        super("http://localhost:50135/TransactionManagerService.svc");
        clientClassNameLower = "transactionmanager";
    }

    @Override
    public void addProductToCart(int productId, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Request<Void> request = new Request<>(Void.class, 1);
        request.requestUrl = baseAddress + "/AddProductToCart";
        request.httpPostVerb = HttpPostVerb.POST;
        request.todoAfterResponseReceived = todoAfterResponseReceived;
        request.todoIfResponseFailed = todoIfResponseFailed;
        request.requestData.put("productId", productId);

        sendRequest(request);
    }

    @Override
    public void updateProductInCart(int productInOrderId, int newHowMany, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Request<Void> request = new Request<>(Void.class, 2);
        request.requestUrl = baseAddress + "/UpdateProductInCart";
        request.httpPostVerb = HttpPostVerb.PUT;
        request.todoAfterResponseReceived = todoAfterResponseReceived;
        request.todoIfResponseFailed = todoIfResponseFailed;
        request.requestData.put("productInOrderId", productInOrderId);
        request.requestData.put("newHowMany", newHowMany);

        sendRequest(request);
    }

    @Override
    public void removeUsersCart(int userOrderId, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Request<Void> request = new Request<>(Void.class, 1);
        request.requestUrl = baseAddress + "/RemoveUsersCart";
        request.httpPostVerb = HttpPostVerb.DELETE;
        request.todoAfterResponseReceived = todoAfterResponseReceived;
        request.todoIfResponseFailed = todoIfResponseFailed;
        request.requestData.put("userOrderId", userOrderId);

        sendRequest(request);
    }

    @Override
    public void removeUsersAllCarts(Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Request<Void> request = new Request<>(Void.class);
        request.requestUrl = baseAddress + "/RemoveUsersAllCarts";
        request.httpPostVerb = HttpPostVerb.DELETE;
        request.todoAfterResponseReceived = todoAfterResponseReceived;
        request.todoIfResponseFailed = todoIfResponseFailed;

        sendRequest(request);
    }

    @Override
    public void removeProductFromCart(int productInOrderId, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Request<Void> request = new Request<>(Void.class, 1);
        request.requestUrl = baseAddress + "/RemoveProductFromCart";
        request.httpPostVerb = HttpPostVerb.DELETE;
        request.todoAfterResponseReceived = todoAfterResponseReceived;
        request.todoIfResponseFailed = todoIfResponseFailed;
        request.requestData.put("productInOrderId", productInOrderId);

        sendRequest(request);
    }

    @Override
    public void sendOrder(int userOrderId, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Request<Void> request = new Request<>(Void.class, 1);
        request.requestUrl = baseAddress + "/SendOrder";
        request.httpPostVerb = HttpPostVerb.PUT;
        request.todoAfterResponseReceived = todoAfterResponseReceived;
        request.todoIfResponseFailed = todoIfResponseFailed;
        request.requestData.put("userOrderId", userOrderId);

        sendRequest(request);

    }

    @Override
    public void addExchangeProduct(int productId, int userOrderId, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Request<Void> request = new Request<>(Void.class, 2);
        request.requestUrl = baseAddress + "/AddExchangeProduct";
        request.httpPostVerb = HttpPostVerb.POST;
        request.todoAfterResponseReceived = todoAfterResponseReceived;
        request.todoIfResponseFailed = todoIfResponseFailed;
        request.requestData.put("productId", productId);
        request.requestData.put("userOrderId", userOrderId);

        sendRequest(request);

    }

    @Override
    public void updateExchangeProduct(int productInOrderId, int newHowMany, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Request<Void> request = new Request<>(Void.class, 2);
        request.requestUrl = baseAddress + "/UpdateExchangeProduct";
        request.httpPostVerb = HttpPostVerb.PUT;
        request.todoAfterResponseReceived = todoAfterResponseReceived;
        request.todoIfResponseFailed = todoIfResponseFailed;
        request.requestData.put("productInOrderId", productInOrderId);
        request.requestData.put("newHowMany", newHowMany);

        sendRequest(request);

    }

    @Override
    public void removeExchangeProduct(int productInOrderId, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Request<Void> request = new Request<>(Void.class, 1);
        request.requestUrl = baseAddress + "/RemoveExchangeProduct";
        request.httpPostVerb = HttpPostVerb.DELETE;
        request.todoAfterResponseReceived = todoAfterResponseReceived;
        request.todoIfResponseFailed = todoIfResponseFailed;
        request.requestData.put("productInOrderId", productInOrderId);

        sendRequest(request);

    }

    @Override
    public void removeExchangeCart(int userOrderId, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Request<Void> request = new Request<>(Void.class, 1);
        request.requestUrl = baseAddress + "/RemoveExchangeCart";
        request.httpPostVerb = HttpPostVerb.DELETE;
        request.todoAfterResponseReceived = todoAfterResponseReceived;
        request.todoIfResponseFailed = todoIfResponseFailed;
        request.requestData.put("userOrderId", userOrderId);

        sendRequest(request);

    }

    @Override
    public void sendExchangeOffer(int userOrderId, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Request<Void> request = new Request<>(Void.class, 1);
        request.requestUrl = baseAddress + "/SendExchangeOffer";
        request.httpPostVerb = HttpPostVerb.PUT;
        request.todoAfterResponseReceived = todoAfterResponseReceived;
        request.todoIfResponseFailed = todoIfResponseFailed;
        request.requestData.put("userOrderId", userOrderId);

        sendRequest(request);

    }

    @Override
    public void finalizeOrderWithoutExchange(int userOrderId, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Request<Void> request = new Request<>(Void.class, 1);
        request.requestUrl = baseAddress + "/FinalizeOrderWithoutExchange";
        request.httpPostVerb = HttpPostVerb.PUT;
        request.todoAfterResponseReceived = todoAfterResponseReceived;
        request.todoIfResponseFailed = todoIfResponseFailed;
        request.requestData.put("userOrderId", userOrderId);

        sendRequest(request);

    }

    @Override
    public void finalizeOrderAcceptExchange(int userOrderId, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Request<Void> request = new Request<>(Void.class, 1);
        request.requestUrl = baseAddress + "/FinalizeOrderAcceptExchange";
        request.httpPostVerb = HttpPostVerb.PUT;
        request.todoAfterResponseReceived = todoAfterResponseReceived;
        request.todoIfResponseFailed = todoIfResponseFailed;
        request.requestData.put("userOrderId", userOrderId);

        sendRequest(request);

    }

    @Override
    public void finalizeOrderDenyExchange(int userOrderId, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Request<Void> request = new Request<>(Void.class, 1);
        request.requestUrl = baseAddress + "/FinalizeOrderDenyExchange";
        request.httpPostVerb = HttpPostVerb.PUT;
        request.todoAfterResponseReceived = todoAfterResponseReceived;
        request.todoIfResponseFailed = todoIfResponseFailed;
        request.requestData.put("userOrderId", userOrderId);

        sendRequest(request);

    }

    @Override
    public void closeOrderSuccessful(int userOrderId, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Request<Void> request = new Request<>(Void.class, 1);
        request.requestUrl = baseAddress + "/CloseOrderSuccessful";
        request.httpPostVerb = HttpPostVerb.PUT;
        request.todoAfterResponseReceived = todoAfterResponseReceived;
        request.todoIfResponseFailed = todoIfResponseFailed;
        request.requestData.put("userOrderId", userOrderId);

        sendRequest(request);

    }

    @Override
    public void closeOrderUnsuccessful(int userOrderId, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Request<Void> request = new Request<>(Void.class, 1);
        request.requestUrl = baseAddress + "/CloseOrderUnsuccessful";
        request.httpPostVerb = HttpPostVerb.PUT;
        request.todoAfterResponseReceived = todoAfterResponseReceived;
        request.todoIfResponseFailed = todoIfResponseFailed;
        request.requestData.put("userOrderId", userOrderId);

        sendRequest(request);

    }

    @Override
    public void getUsersCartsVM(Integer customerId, Integer vendorId, Action_1<List<UserOrderPLVM>> todoWithResponse, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Type type = new TypeToken<ArrayList<UserOrderPLVM>>()
        {
        }.getType();

        Request<List<UserOrderPLVM>> request = new Request<>(type, 2);
        request.requestUrl = baseAddress + "/GetUsersCartsVM";
        request.todoWithResponse = todoWithResponse;
        request.todoIfResponseFailed = todoIfResponseFailed;
        request.requestData.put("customerId", customerId);
        request.requestData.put("vendorId", vendorId);

        sendRequest(request);
    }

    @Override
    public void getUsersInProgressOrdersVM(Integer customerId, Integer vendorId, Action_1<List<UserOrderPLVM>> todoWithResponse, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Type type = new TypeToken<ArrayList<UserOrderPLVM>>()
        {
        }.getType();

        Request<List<UserOrderPLVM>> request = new Request<>(type, 2);
        request.requestUrl = baseAddress + "/GetUsersInProgressOrdersVM";
        request.todoWithResponse = todoWithResponse;
        request.todoIfResponseFailed = todoIfResponseFailed;
        request.requestData.put("customerId", customerId);
        request.requestData.put("vendorId", vendorId);

        sendRequest(request);
    }

    @Override
    public void getUsersFinishedTransactionsVM(Integer customerId, Integer vendorId, Action_1<List<UserOrderPLVM>> todoWithResponse, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Type type = new TypeToken<ArrayList<UserOrderPLVM>>()
        {
        }.getType();

        Request<List<UserOrderPLVM>> request = new Request<>(type, 2);
        request.requestUrl = baseAddress + "/GetUsersFinishedTransactionsVM";
        request.todoWithResponse = todoWithResponse;
        request.todoIfResponseFailed = todoIfResponseFailed;
        request.requestData.put("customerId", customerId);
        request.requestData.put("vendorId", vendorId);

        sendRequest(request);
    }
}


