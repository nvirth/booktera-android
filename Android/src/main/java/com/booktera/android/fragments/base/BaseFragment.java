package com.booktera.android.fragments.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.booktera.android.common.utils.Utils;

import java.io.Serializable;

public abstract class BaseFragment extends Fragment
{
    protected final String tag = ((Object) this).getClass().toString();

    /**
     * Extracts the given parameter from the 'getArguments()' Bundle. Throws error if it/the bundle is null.
     */
    protected String extractStringParam(String paramName)
    {
        String errorMsg = "You can't call this fragment without passing this argument: " + paramName;
        Bundle bundle = getArguments();

        return Utils.extractStringParam(bundle, paramName, tag, errorMsg);
    }

    /**
     * Extracts the given parameter from the 'getArguments()' Bundle. Throws error if it/the bundle is null.
     */
    protected Serializable extractSerializableParam(String paramName)
    {
        String errorMsg = "You can't call this fragment without passing this argument: " + paramName;
        Bundle bundle = getArguments();

        return Utils.extractSerializableParam(bundle, paramName, tag, errorMsg);
    }

    /**
     * Extracts the given parameter from the 'getArguments()' Bundle. Throws error if the bundle is null.
     * If there is no boolean value in the bundle with the given key, returns false.
     */
    protected boolean extractBooleanParam(String paramName)
    {
        String errorMsg = "You can't call this fragment without passing this argument: " + paramName;
        Bundle bundle = getArguments();

        return Utils.extractBooleanParam(bundle, paramName, tag, errorMsg);
    }

    /**
     * Extracts the given parameter from the 'getArguments()' Bundle. Throws error if the bundle is null.
     * If there is no int value in the bundle with the given key, returns 0.
     */
    protected int extractIntParam(String paramName)
    {
        String errorMsg = "You can't call this fragment without passing this argument: " + paramName;
        Bundle bundle = getArguments();

        return Utils.extractIntParam(bundle, paramName, tag, errorMsg);
    }
}

