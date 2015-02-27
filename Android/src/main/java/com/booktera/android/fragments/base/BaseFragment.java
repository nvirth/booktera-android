package com.booktera.android.fragments.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.booktera.android.R;
import com.booktera.android.common.Constants;
import com.booktera.android.common.utils.Utils;

public abstract class BaseFragment extends Fragment
{
    protected final String tag = ((Object) this).getClass().toString();

    /**
     * Extracts the given parameter from the 'getArguments()' Bundle. Throws error if it/the bundle is null.
     */
    protected String extractParam(String paramName)
    {
        String errorMsg = "You can't call this fragment without passing this argument: " + paramName;
        Bundle bundle = getArguments();

        return Utils.extractParam(bundle, paramName, tag, errorMsg);
    }
}

