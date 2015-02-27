package com.booktera.android.common.models.base;

import com.booktera.androidclientproxy.lib.models.ProductModels.BookBlockPLVM;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Norbert on 2015.02.09..
 */
public class MapCache<TKey, TValue>
{
    protected final String tag = ((Object) this).getClass().toString();

    private Map<TKey, TValue> _cache = new HashMap<>();
    protected TValue getValue(TKey key)
    {
        return _cache.get(key);
    }
    protected void setValue(TKey key, TValue value)
    {
        _cache.put(key, value);
    }
}
