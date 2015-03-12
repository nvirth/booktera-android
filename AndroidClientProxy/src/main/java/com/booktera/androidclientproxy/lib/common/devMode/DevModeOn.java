package com.booktera.androidclientproxy.lib.common.devMode;


import com.booktera.androidclientproxy.lib.common.Config;
import com.booktera.androidclientproxy.lib.proxy.Request;
import com.booktera.androidclientproxy.lib.proxy.base.RestServiceClientBase;

/**
 * This is a decorated version of {@link DevModeOn_Core}.
 * This only calls the DevMode methods if them are enabled in {@link Config}
 */
public class DevModeOn implements IDevMode
{
    private IDevMode _core;

    public DevModeOn(IDevMode _core)
    {
        this._core = _core;
    }

    // --

    @Override
    public <T> boolean applyMockData(Request<T> r, RestServiceClientBase rscb)
    {
        if (Config.DevModeEnable.MockDataIfNoConnection)
            return _core.applyMockData(r, rscb);

        return false;
    }
}
