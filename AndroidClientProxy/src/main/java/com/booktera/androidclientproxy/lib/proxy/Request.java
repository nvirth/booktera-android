package com.booktera.androidclientproxy.lib.proxy;

import com.booktera.androidclientproxy.lib.enums.HttpPostVerb;
import org.apache.http.HttpResponse;
import com.booktera.androidclientproxy.lib.utils.Action;
import com.booktera.androidclientproxy.lib.utils.Action_1;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Request<T>
{
    public final Type type;

    public Request(Type type)
    {
        this(type, 0);
    }
    public Request(Class<T> clazz)
    {
        this(clazz, 0);
    }
    public Request(Class<T> clazz, int dataCount)
    {
        this((Type) clazz, dataCount);
    }
    public Request(Type type, int dataCount)
    {
        this.type = type;
        this.requestData = new HashMap<>(dataCount);
    }

    public String requestUrl;
    public HttpPostVerb httpPostVerb = HttpPostVerb.NON_POST__GET;
    public Action_1<T> todoWithResponse;

    /**
     * Action_1[HttpResponse: Response]. Null if there was some network error.
     */
    public Action_1<HttpResponse> todoIfResponseFailed;
    public Action todoAfterResponseReceived;

    /**
     * There are 2 ways to give data here. <br />
     * You can use raw key-value pairs. E.g.: <br />
     * "productId": 1, "userId": 2 <br />
     * Or you can give here a preformatted value under empty string key. E.g.:  <br />
     * "{productId: 1, userId: 2}"<br />
     * In this case, the raw values will be ignored <br />
     */
    public Map<String, Object> requestData;
}


