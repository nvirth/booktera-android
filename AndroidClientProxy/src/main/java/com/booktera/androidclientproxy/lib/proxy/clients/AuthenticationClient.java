package com.booktera.androidclientproxy.lib.proxy.clients;

import com.booktera.androidclientproxy.lib.proxy.Request;
import com.booktera.androidclientproxy.lib.proxy.base.RestServiceClientBase;
import com.booktera.androidclientproxy.lib.proxy.clients.interfaces.IAuthenticationClient;
import com.booktera.androidclientproxy.lib.utils.Action;
import com.booktera.androidclientproxy.lib.utils.Action_2;

public class AuthenticationClient extends RestServiceClientBase implements IAuthenticationClient
{
    public AuthenticationClient()
    {
        super("http://localhost:50135/Authentication/BookteraAuthenticationService.svc");
        clientClassNameLower = "authentication";
    }

    // -- login

    /**
     * Logs in the user with the given credentials.
     * Example: <br />
     * http://localhost:50135/Authentication/BookteraAuthenticationService.svc/LoginAndGetId?userName=zomidudu&password=asdqwe123&persistent=true
     *
     * @param todoWithResult Action_2[Boolean: wasSuccessful, Integer: userId]
     */
    @Override
    public void login(String userName, String password, boolean persistent, Action_2<Boolean, Integer> todoWithResult)
    {
        Request<LoginAndGetIdResponse> request = new Request<>(LoginAndGetIdResponse.class, 3);
        request.requestUrl = baseAddress + "/LoginAndGetId";
        request.requestData.put("userName", userName);
        request.requestData.put("password", password);
        request.requestData.put("persistent", persistent);
        request.todoWithResponse = res -> {
            if (todoWithResult != null)
                todoWithResult.run(res.wasSuccessful, res.userId);
        };

        sendRequest(request);
    }

    static class LoginAndGetIdResponse
    {
        public boolean wasSuccessful;
        public int userId;
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


