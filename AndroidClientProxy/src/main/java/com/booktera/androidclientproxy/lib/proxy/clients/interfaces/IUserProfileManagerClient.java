package com.booktera.androidclientproxy.lib.proxy.clients.interfaces;

import com.booktera.androidclientproxy.lib.models.UserProfileEditVM;
import com.booktera.androidclientproxy.lib.utils.Action;
import com.booktera.androidclientproxy.lib.utils.Action_1;
import org.apache.http.HttpResponse;

/**
 * Created by Norbert on 2015.02.01..
 */
public interface IUserProfileManagerClient
{
    void getForEdit(Action_1<UserProfileEditVM> todoWithResult);
    void update(UserProfileEditVM data, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed);
}
