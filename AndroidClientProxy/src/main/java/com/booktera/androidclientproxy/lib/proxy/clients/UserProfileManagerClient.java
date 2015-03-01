package com.booktera.androidclientproxy.lib.proxy.clients;

import com.booktera.androidclientproxy.lib.enums.HttpPostVerb;
import com.booktera.androidclientproxy.lib.models.UserProfileEditVM;
import com.booktera.androidclientproxy.lib.proxy.clients.interfaces.IUserProfileManagerClient;
import org.apache.http.HttpResponse;
import com.booktera.androidclientproxy.lib.utils.Action;
import com.booktera.androidclientproxy.lib.utils.Action_1;
import com.booktera.androidclientproxy.lib.proxy.Request;
import com.booktera.androidclientproxy.lib.proxy.base.RestServiceClientBase;

public class UserProfileManagerClient extends RestServiceClientBase implements IUserProfileManagerClient
{
    public UserProfileManagerClient()
    {
        super("http://localhost:50135/EntityManagers/UserProfileManagerService.svc");
        clientClassNameLower = "userprofilemanager";
    }

    @Override
    public void getForEdit(Action_1<UserProfileEditVM> todoWithResult)
    {
        Request<UserProfileEditVM> request = new Request<>(UserProfileEditVM.class);
        request.requestUrl = baseAddress + "/GetForEdit";
        request.todoWithResponse = todoWithResult;

        sendRequest(request);
    }

    @Override
    public void update(UserProfileEditVM data, Action todoAfterResponseReceived, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Request<Void> request = new Request<>(Void.class, 1);
        request.requestUrl = baseAddress + "/Update";
        request.httpPostVerb = HttpPostVerb.PUT;
        request.todoAfterResponseReceived = todoAfterResponseReceived;
        request.todoIfResponseFailed = todoIfResponseFailed;
        request.requestData.put("userProfileEdit", data);

        sendRequest(request);
    }
}


