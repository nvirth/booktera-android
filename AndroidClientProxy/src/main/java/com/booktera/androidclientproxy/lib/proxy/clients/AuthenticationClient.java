package com.booktera.androidclientproxy.lib.proxy.clients;

import com.booktera.androidclientproxy.lib.proxy.Request;
import com.booktera.androidclientproxy.lib.proxy.base.RestServiceClientBase;
import com.booktera.androidclientproxy.lib.models.dto.LoginAndGetIdResponse;
import com.booktera.androidclientproxy.lib.proxy.clients.interfaces.IAuthenticationClient;
import com.booktera.androidclientproxy.lib.utils.Action;
import com.booktera.androidclientproxy.lib.utils.Action_1;

public class AuthenticationClient extends RestServiceClientBase implements IAuthenticationClient
{
    public AuthenticationClient()
    {
        super("http://localhost:50135/Authentication/BookteraAuthenticationService.svc");
        clientClassNameLower = "authentication";
    }

    // -- login

    /**
     * http://localhost:50135/Authentication/BookteraAuthenticationService.svc/LoginAndGetId?userName=zomidudu&password=asdqwe123&persistent=true
     */
    @Override
    public void login(String userName, String password, boolean persistent, Action_1<LoginAndGetIdResponse> todoWithResult)
    {
        Request<LoginAndGetIdResponse> request = new Request<>(LoginAndGetIdResponse.class, 3);
        request.requestUrl = baseAddress + "/LoginAndGetId";
        request.todoWithResponse = todoWithResult;
        request.requestData.put("userName", userName);
        request.requestData.put("password", password);
        request.requestData.put("persistent", persistent);

        sendRequest(request);
    }

    // -- logout

    @Override
    public void logout(Action todoAfterResponseReceived)
    {
        Request request = new Request<>(Void.class);
        request.requestUrl = baseAddress + "/Logout";
        request.todoAfterResponseReceived = todoAfterResponseReceived;

        sendRequest(request);
    }
}


