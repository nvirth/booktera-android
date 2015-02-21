package com.booktera.androidclientproxy.lib.proxy.clients;

import com.booktera.androidclientproxy.lib.proxy.Request;
import com.booktera.androidclientproxy.lib.proxy.base.RestServiceClientBase;
import com.booktera.androidclientproxy.lib.proxy.clients.interfaces.IImageManagerClient;
import com.booktera.androidclientproxy.lib.utils.Action_1;

public class ImageManagerClient extends RestServiceClientBase implements IImageManagerClient
{
    public ImageManagerClient()
    {
        super("http://localhost:50135/EntityManagers/ImageManagerService.svc");
        clientClassNameLower = "imagemanager";
    }

    @Override
    public void getUsersAvatar(Action_1<String> todoWithResult)
    {
        Request<String> request = new Request<>(String.class);
        request.requestUrl = baseAddress + "/GetUsersAvatar";
        request.todoWithResponse = todoWithResult;

        sendRequest(request);
    }
}


