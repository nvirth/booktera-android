package com.booktera.androidclientproxy.lib.proxy.clients.interfaces;

import com.booktera.androidclientproxy.lib.models.dto.LoginAndGetIdResponse;
import com.booktera.androidclientproxy.lib.utils.Action;
import com.booktera.androidclientproxy.lib.utils.Action_1;

public interface IAuthenticationClient
{
    void login(String userName, String password, boolean persistent, Action_1<LoginAndGetIdResponse> todoWithResult);
    void logout(Action todoAfterResponseReceived);
}
