package com.booktera.androidclientproxy.lib.proxy.clients.interfaces;

import com.booktera.androidclientproxy.lib.models.UserOrderPLVM;
import com.booktera.androidclientproxy.lib.utils.Action;
import com.booktera.androidclientproxy.lib.utils.Action_1;
import org.apache.http.HttpResponse;

import java.util.List;

/**
 * Created by Norbert on 2015.02.01..
 */
public interface ITransactionManagerClient
{
    void addProductToCart(int productId, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed);
    void updateProductInCart(int productInOrderId, int newHowMany, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed);
    void removeUsersCart(int userOrderId, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed);
    void removeUsersAllCarts(Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed);
    void removeProductFromCart(int productInOrderId, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed);
    void sendOrder(int userOrderId, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed);
    void addExchangeProduct(int productId, int userOrderId, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed);
    void updateExchangeProduct(int productInOrderId, int newHowMany, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed);
    void removeExchangeProduct(int productInOrderId, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed);
    void removeExchangeCart(int userOrderId, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed);
    void sendExchangeOffer(int userOrderId, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed);
    void finalizeOrderWithoutExchange(int userOrderId, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed);
    void finalizeOrderAcceptExchange(int userOrderId, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed);
    void finalizeOrderDenyExchange(int userOrderId, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed);
    void closeOrderSuccessful(int userOrderId, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed);
    void closeOrderUnsuccessful(int userOrderId, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed);

    void getUsersCartsVM(Integer customerId, Integer vendorId, Action_1<List<UserOrderPLVM>> todoWithResponse, Action_1<HttpResponse> todoIfResponseFailed);
    void getUsersInProgressOrdersVM(Integer customerId, Integer vendorId, Action_1<List<UserOrderPLVM>> todoWithResponse, Action_1<HttpResponse> todoIfResponseFailed);
    void getUsersFinishedTransactionsVM(Integer customerId, Integer vendorId, Action_1<List<UserOrderPLVM>> todoWithResponse, Action_1<HttpResponse> todoIfResponseFailed);
}
