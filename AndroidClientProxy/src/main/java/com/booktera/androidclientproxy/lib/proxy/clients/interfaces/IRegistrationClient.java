package com.booktera.androidclientproxy.lib.proxy.clients.interfaces;

import com.booktera.androidclientproxy.lib.utils.Action;
import com.booktera.androidclientproxy.lib.utils.Action_1;
import org.apache.http.HttpResponse;

/**
 * Created by Norbert on 2015.02.01..
 */
public interface IRegistrationClient
{
    void register(String userName, String email, String password, String passwordRepeat, Action todoAfterReceiveReply, Action_1<HttpResponse> todoIfResponseFailed);
}
