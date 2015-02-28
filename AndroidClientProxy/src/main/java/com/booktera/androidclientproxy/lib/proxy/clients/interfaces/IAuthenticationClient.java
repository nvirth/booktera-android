package com.booktera.androidclientproxy.lib.proxy.clients.interfaces;

import com.booktera.androidclientproxy.lib.utils.Action;
import com.booktera.androidclientproxy.lib.utils.Action_2;

public interface IAuthenticationClient
{
    /**
     * Logs in the user with the given credentials.
     * Example: <br />
     * http://localhost:50135/Authentication/BookteraAuthenticationService.svc/LoginAndGetId?userName=zomidudu&password=asdqwe123&persistent=true
     *
     * @param todoWithResult Action_2[Boolean: wasSuccessful, Integer: userId]
     */
    void login(String userName, String password, boolean persistent, Action_2<Boolean, Integer> todoWithResult);
    void logout(Action todoAfterResponseReceived);
}
