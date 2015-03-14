package com.booktera.android.common.models.base;

import com.booktera.android.common.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Norbert on 2015.02.09..
 */
public class MapCache<TKey, TValue>
{
    protected final String tag = ((Object) this).getClass().toString();

    private Map<TKey, TValue> _cache = new HashMap<>();
    private Map<TKey, Boolean> _cacheValidity = new HashMap<>();

    protected TValue getValue(TKey key)
    {
        return _cache.get(key);
    }
    protected void setValue(TKey key, TValue value)
    {
        _cache.put(key, value);

        // We assume the data is valid at the time we save it to cache
        _cacheValidity.put(key, true);
    }
    protected void removeValue(TKey key)
    {
        _cache.remove(key);
    }

    /**
     * Not set values are handled as valid
     */
    protected boolean isValid(TKey key)
    {
        return Utils.ifNull(_cacheValidity.get(key), true);
    }
    protected void invalidate(TKey key)
    {
        _cacheValidity.put(key, false);
    }
}
