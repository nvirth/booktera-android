package com.booktera.androidclientproxy.lib.common.devMode;

import com.booktera.androidclientproxy.lib.proxy.Request;
import com.booktera.androidclientproxy.lib.proxy.base.RestServiceClientBase;

/**
 * Dummy class for if developer mode is off
 */
class DevModeOff implements IDevMode
{
    @Override
    public <T> boolean applyMockData(Request<T> r, RestServiceClientBase rscb)
    {
        return false;
    }
}
