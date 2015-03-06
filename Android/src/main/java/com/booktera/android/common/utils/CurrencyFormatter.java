package com.booktera.android.common.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;
import com.booktera.android.BookteraApplication;
import com.booktera.android.R;
import com.booktera.android.common.Config;
import com.booktera.androidclientproxy.lib.models.ProductModels.InBookBlockPVM;
import com.booktera.androidclientproxy.lib.utils.Action_1;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormatter
{
    private static NumberFormat numberFormatHu = NumberFormat.getCurrencyInstance(new Locale("hu", "HU"));
    public static CurrencyFormatter Instance = factoryMethod();

    private static CurrencyFormatter factoryMethod(){
        return new CurrencyFormatter();
    }

    // --

    private NumberFormat formatter;

    protected CurrencyFormatter()
    {
        formatter = numberFormatHu;
    }

    // --

    //region Delegated methods
    public String format(double value)
    {
        return formatter.format(value);
    }
    public StringBuffer format(double value, StringBuffer buffer, FieldPosition field)
    {
        return formatter.format(value, buffer, field);
    }
    public String format(long value)
    {
        return formatter.format(value);
    }
    public StringBuffer format(long value, StringBuffer buffer, FieldPosition field)
    {
        return formatter.format(value, buffer, field);
    }
    public StringBuffer format(Object object, StringBuffer buffer, FieldPosition field)
    {
        return formatter.format(object, buffer, field);
    }
    //endregion
}
