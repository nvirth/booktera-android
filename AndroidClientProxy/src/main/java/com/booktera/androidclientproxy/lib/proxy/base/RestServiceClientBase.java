package com.booktera.androidclientproxy.lib.proxy.base;

import android.content.res.Resources;
import android.net.http.AndroidHttpClient;
import android.util.Log;
import com.booktera.androidclientproxy.lib.R;
import com.booktera.androidclientproxy.lib.proxy.Request;
import com.booktera.androidclientproxy.lib.utils.Helpers;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.booktera.androidclientproxy.lib.enums.HttpPostVerb;
import com.booktera.androidclientproxy.lib.enums.HttpRequestHeader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import com.booktera.androidclientproxy.lib.utils.Action_1;

import java.io.*;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * This class is used for communicating with the backend REST services.
 * Note: Initialization required, by calling the setDefaultErrorMsgAction method!
 */
public abstract class RestServiceClientBase
{
    public static boolean isMockingEnabled = true;

    private static final String CONTENT_TYPE_JSON = "application/json; charset=utf-8";
    private static final String ENCODING = "UTF-8";
    private static final String tag = RestServiceClientBase.class.toString();
    private static final String userAgent = System.getProperty("http.agent");
    private static final CookieStore cookieStore = new BasicCookieStore();

    private static final String actualIp = "192.168.1.103";
    protected final String baseAddress;
    protected String clientClassNameLower;
    protected String resourcePackageName;

    // -- Props

    //region sendErrorMsgAction
    private static Action_1<String> sendErrorMsgAction;
    public static Action_1<String> getSendErrorMsgAction()
    {
        return sendErrorMsgAction;
    }
    public static void setSendErrorMsgAction(Action_1<String> sendErrorMsgAction)
    {
        RestServiceClientBase.sendErrorMsgAction = sendErrorMsgAction;
    }
    //endregion

    //region resources
    private static Resources resources;
    public static Resources getResources()
    {
        return resources;
    }
    public static void setResources(Resources resources)
    {
        RestServiceClientBase.resources = resources;
    }
    //endregion

    // -- Ctor

    public RestServiceClientBase(String baseAddress)
    {
        if (resources == null || sendErrorMsgAction == null)
        {
            String msg = "RestServiceClientBase.resources and .sendErrorMsgAction must be initialized";
            RuntimeException e = new RuntimeException(msg);
            Log.e(tag, msg, e);
            throw e;
        }

        // It will be "com.booktera.android" every time, but safe to get it this way :)
        resourcePackageName = resources.getResourcePackageName(R.raw.productmanager_getmainhighlighteds);

        this.baseAddress = baseAddress.replace("localhost", actualIp);
    }

    // -- Methods

    protected <T> void sendRequest(Request<T> r)
    {
        Executors.newSingleThreadExecutor().submit(
            () -> {
                HttpRequestBase request;
                HttpResponse response;
                AndroidHttpClient httpClient = null;
                int statusCode;

                try
                {
                    request = prepareHttpRequest(r);
                    request.setHeader(HttpRequestHeader.CacheControl.toString(), "no-cache");
                    request.setHeader(HttpRequestHeader.IfModifiedSince.toString(), new Date().toString());

                    HttpContext httpContext = new BasicHttpContext();
                    httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

                    httpClient = AndroidHttpClient.newInstance(userAgent);
                    HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 3000);
                    HttpConnectionParams.setSoTimeout(httpClient.getParams(), 5000);

                    Log.v(tag, "Sending request to: " + r.requestUrl);
                    response = httpClient.execute(request, httpContext);
                    Log.v(tag, "Response received from: " + r.requestUrl);

                    statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == HttpStatus.SC_OK)
                    {
                        HttpEntity entity = response.getEntity();
                        InputStream is = entity == null ? null : entity.getContent();

                        handleResponse(r, is);
                    }
                    else
                    {
                        handleResponseFail(r.todoIfResponseFailed, response, statusCode);
                    }
                }
                catch (IOException e)
                {
                    Log.e(tag, "sendRequest", e);

                    boolean mockAppliedSuccessfully = false;
                    if (isMockingEnabled)
                        mockAppliedSuccessfully = applyMockData(r);

                    if (mockAppliedSuccessfully)
                        showMockAppliedErrorMessage();
                    else
                        showDefaultErrorMessage();
                }
                finally
                {
                    if (httpClient != null)
                        httpClient.close();
                }
            }
        );
    }
    private <T> void handleResponse(Request<T> r, InputStream is) throws IOException
    {
        if (r.type != Void.class)
        {
            T responseEntity = fetchResponse(is, r.type);
            if (r.todoWithResponse != null)
                r.todoWithResponse.run(responseEntity);
        }
        if (r.todoAfterResponseReceived != null)
            r.todoAfterResponseReceived.run();
    }
    private <T> boolean applyMockData(Request<T> r)
    {
        try
        {
            String path = new URI(r.requestUrl).getPath(); //e.g.: "/EntityManagers/ProductManagerService.svc/GetMainHighlighteds"
            String regex = "^.*/(\\w+)Service\\.svc/(\\w+)$";
            String resName = path.replaceFirst(regex, "$1_$2").toLowerCase();

            int rawMockResId = resources.getIdentifier(resName, "raw", resourcePackageName);
            InputStream is = resources.openRawResource(rawMockResId);

            handleResponse(r, is);
            return true;
        }
        catch (Exception e)
        {
            Log.e(tag, "", e);
            return false;
        }
    }
    private void handleResponseFail(Action_1<HttpResponse> todoIfResponseFailed, HttpResponse response, int statusCode)
    {
        Log.e(tag, "Request ended with code: " + statusCode);
        boolean handled = false;

        if (statusCode == HttpStatus.SC_FORBIDDEN
            || statusCode == HttpStatus.SC_UNAUTHORIZED)
        {
            //TODO átirányítás a Login page-re!
            handled = true;
            throw new UnsupportedOperationException();
        }

        if (todoIfResponseFailed != null && !handled)
            todoIfResponseFailed.run(response);
        else
            showDefaultErrorMessage();
    }
    private void showDefaultErrorMessage()
    {
        String errorMsg = resources.getString(R.string.default_connection_error_msg);
        sendErrorMsgAction.run(errorMsg);
    }
    private void showMockAppliedErrorMessage()
    {
        String errorMsg = resources.getString(R.string.mock_applied_error_msg);
        sendErrorMsgAction.run(errorMsg);
    }
    private <T> T fetchResponse(InputStream is, Class<T> type) throws IOException
    {
        if (is == null)
            throw new RuntimeException(tag + ": The fn fetchResponse(...) shouldn't get null InputStream arg. ");

        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            // This would be the fastest version
            //return new Gson().fromJson(reader, type);
            // But while developing, we sometimes want to see the received entity too
            String entityStr = Helpers.readToEnd(reader);
            return new Gson().fromJson(entityStr, type);
        }
        catch (JsonParseException e)
        {
            Log.e(tag, "Could not parse the JSON response. ", e);
            throw e;
        }
    }
    private <T> HttpRequestBase prepareHttpRequest(Request<T> r) throws UnsupportedEncodingException
    //private HttpRequestBase prepareHttpRequest(String url, HttpPostVerb httpPostVerb, Map<String, Object> requestData, String requestData) throws UnsupportedEncodingException
    {
        //request = prepareHttpRequest(r.requestUrl, r.httpPostVerb, r.requestData, r.requestData);
        HttpRequestBase httpRequest;
        switch (r.httpPostVerb)
        {
            case NON_POST__GET:
                r.requestUrl = buildQueryString(r.requestUrl, r.requestData);
                httpRequest = new HttpGet(r.requestUrl);
                break;
            case POST:
                httpRequest = new HttpPost(r.requestUrl);
                break;
            case DELETE:
                httpRequest = new HttpDelete(r.requestUrl);
                break;
            case PUT:
                httpRequest = new HttpPut(r.requestUrl);
                break;
            default:
                throw new RuntimeException("Unknown http request type: " + r.httpPostVerb);
        }

        if (r.httpPostVerb != HttpPostVerb.NON_POST__GET)
            addPostData(r.requestData, httpRequest);

        return httpRequest;
    }
    private void addPostData(Map<String, Object> requestData, HttpRequestBase httpRequest) throws UnsupportedEncodingException
    {
        if (requestData == null || requestData.isEmpty())
            return;

        String jsonData;
        if (requestData.containsKey("")) // -- Prepared
        {
            jsonData = new Gson().toJson(requestData.get(""));

            if (requestData.size() > 1)
            {
                String msg = "The request contained either raw and prepared data elements. Raw elements will be ignored. Data : ";
                msg += new Gson().toJson(requestData);
                Log.w(tag, msg);
            }
        }
        else // -- Raw
        {
            jsonData = new Gson().toJson(requestData);
        }

        HttpEntityEnclosingRequestBase entityRequest = (HttpEntityEnclosingRequestBase) httpRequest;
        StringEntity entity = new StringEntity(jsonData, ENCODING);
        entity.setContentType(CONTENT_TYPE_JSON);
        entityRequest.setEntity(entity);
    }

    public static String buildQueryString(String baseUrl, Map<String, Object> data) throws UnsupportedEncodingException
    {
        if (data == null || data.isEmpty())
            return baseUrl;

        // -- Prepared
        if (data.containsKey(""))
            return baseUrl + data.get("");

        // -- Raw
        StringBuilder sb = new StringBuilder();
        sb.append(baseUrl).append("?");

        for (Map.Entry<String, Object> param : data.entrySet())
        {
            String key = URLEncoder.encode(param.getKey(), ENCODING);
            String value = URLEncoder.encode(param.getValue().toString(), ENCODING);
            sb.append(key).append("=").append(value).append("&");
        }
        sb.deleteCharAt(sb.length() - 1); // Remove last '&'

        return sb.toString();
    }

}
