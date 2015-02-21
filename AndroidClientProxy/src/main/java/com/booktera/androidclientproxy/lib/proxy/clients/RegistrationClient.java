package com.booktera.androidclientproxy.lib.proxy.clients;

import com.booktera.androidclientproxy.lib.models.AccountModels.RegisterVM;
import com.booktera.androidclientproxy.lib.models.UserAddressVM;
import com.booktera.androidclientproxy.lib.proxy.clients.interfaces.IRegistrationClient;
import com.google.gson.Gson;
import com.booktera.androidclientproxy.lib.enums.HttpPostVerb;
import org.apache.http.HttpResponse;
import com.booktera.androidclientproxy.lib.utils.Action;
import com.booktera.androidclientproxy.lib.utils.Action_1;
import com.booktera.androidclientproxy.lib.proxy.Request;
import com.booktera.androidclientproxy.lib.proxy.base.RestServiceClientBase;

public class RegistrationClient extends RestServiceClientBase implements IRegistrationClient
{
    public RegistrationClient()
    {
        super("http://localhost:50135/RegistrationManagerService.svc");
        clientClassNameLower = "registration";
    }

    @Override
    public void register(String userName, String email, String password, String passwordRepeat, Action todoAfterReceiveReply, Action_1<HttpResponse> todoIfResponseFailed)
    {
        Request<Void> request = new Request<>(Void.class, 1);
        request.requestUrl = baseAddress + "/RegisterUser";
        request.requestData.put("", register_BuildPostData(userName, email, password, passwordRepeat));
        request.httpPostVerb = HttpPostVerb.POST;
        request.todoAfterResponseReceived = todoAfterReceiveReply;
        request.todoIfResponseFailed = todoIfResponseFailed;

        sendRequest(request);
    }

    private String register_BuildPostData(String userName, String email, String password, String passwordRepeat)
    {
        RegisterVM registerVM = new RegisterVM();
        registerVM.setUserName(userName);
        registerVM.setEMail(email);
        registerVM.setPassword(password);
        registerVM.setConfirmPassword(passwordRepeat);

        // cheat
        UserAddressVM userAddressVM = new UserAddressVM();
        userAddressVM.setCity("MyCity");
        userAddressVM.setCountry("MyCountry");
        userAddressVM.setIsDefault(true);
        userAddressVM.setStreetAndHouseNumber("MyStreet 1");
        userAddressVM.setZipCode("1010");
        registerVM.setUserAddress(userAddressVM);

        return new Gson().toJson(registerVM);
    }
}


