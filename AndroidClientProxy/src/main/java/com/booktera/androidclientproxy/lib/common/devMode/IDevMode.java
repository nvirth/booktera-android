package com.booktera.androidclientproxy.lib.common.devMode;


import com.booktera.androidclientproxy.lib.proxy.Request;
import com.booktera.androidclientproxy.lib.proxy.base.RestServiceClientBase;

public interface IDevMode
{
    <T> boolean applyMockData(Request<T> r, RestServiceClientBase rscb);
}
