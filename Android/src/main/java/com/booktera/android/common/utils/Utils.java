package com.booktera.android.common.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
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
import com.booktera.androidclientproxy.lib.utils.Func_2;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.io.Serializable;

public class Utils
{
    //region ifNull or Empty

    public static <T> T ifNull(T in, T defaultValue)
    {
        if (in == null)
            return defaultValue;
        return in;
    }
    public static String ifNullOrEmpty(String str, String ifNullValue)
    {
        if (TextUtils.isEmpty(str))
            return ifNullValue;
        return str;
    }
    //endregion

    //region setImage

    public static void setUserImage(String imageFile, ImageView imageView)
    {
        setImage(imageFile, imageView, Config.UserImagesUrlDirFormat);
    }
    public static void setProductImage(InBookBlockPVM inBookBlockPVM, ImageView imageView)
    {
        String imageFile = Utils.ifNullOrEmpty(
            inBookBlockPVM.getProduct().getImageUrl(),
            inBookBlockPVM.getProductGroup().getImageUrl()
        );
        setImage(imageFile, imageView, Config.ProductImagesUrlDirFormat);
    }
    private static void setImage(String imageFile, ImageView imageView, String imageDirUrlFormat)
    {
        String url = null;
        try
        {
            url = String.format(imageDirUrlFormat, imageFile);
            UrlImageViewHelper.setUrlDrawable(imageView, url, R.drawable.no_image);
        }
        catch (Exception e)
        {
            Log.w("Utils.setImage", "Image couldn't set. ImageUrl: " + url, e);
        }
    }

    //endregion

    //region showToast

    /**
     * Shows a Toast with length: Toast.LENGTH_SHORT
     */
    public static void showToast(String msg)
    {
        showToast(msg, false);
    }
    public static void showToast(String msg, boolean isLong)
    {
        Toast.makeText(
            BookteraApplication.getAppContext(),
            msg,
            isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT
        ).show();
    }

    //endregion

    //region runInFragmentTransaction
    public static void runInFragmentTransaction(FragmentActivity fragmentActivity, Action_1<FragmentTransaction> action)
    {
        FragmentManager manager = fragmentActivity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        action.run(transaction);

        transaction.commit();
    }
    //endregion

    //region hideKeyboard
    public static void hideKeyboard(Fragment fragment)
    {
        FragmentActivity activity = fragment.getActivity();
        hideKeyboard(activity);
    }
    public static void hideKeyboard(FragmentActivity activity)
    {
        if (activity == null)
            return;

        View focusedView = activity.getCurrentFocus();
        if (focusedView == null)
            return;

        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null)
            return;

        inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
    }
    //endregion

    //region Log
    /**
     * Logs the msg as error, then throws a RuntimeException with it
     */
    public static void error(String msg, String tag)
    {
        com.booktera.androidclientproxy.lib.utils.Utils.error(msg, tag);
    }
    //endregion

    //region extractStringParam
    /**
     * Extracts the given parameter from the Bundle. Throws error if it/the bundle is null.
     */
    public static String extractStringParam(Bundle bundle, String paramName, String tag, String msg)
    {
        //java.lang.NoClassDefFoundError: android.os.BaseBundle
        //return extractParamCore(bundle, paramName, tag, msg, BaseBundle::getString);
        return extractParamCore(bundle, paramName, tag, msg,
            (_bundle, _paramName) ->
                _bundle.getString(_paramName));
    }
    /**
     * Extracts the given parameter from the Bundle. Throws error if it/the bundle is null.
     */
    public static Serializable extractSerializableParam(Bundle bundle, String paramName, String tag, String msg)
    {
        return extractParamCore(bundle, paramName, tag, msg, Bundle::getSerializable);
    }
    /**
     * Extracts the given parameter from the Bundle. Throws error if the bundle is null.
     * If there is no int value in the bundle with the given key, returns 0.
     */
    public static int extractIntParam(Bundle bundle, String paramName, String tag, String msg)
    {
        //java.lang.NoClassDefFoundError: android.os.BaseBundle
        //return extractParamCore(bundle, paramName, tag, msg, BaseBundle::getInt);
        return extractParamCore(bundle, paramName, tag, msg,
            (_bundle, _paramName) ->
                _bundle.getInt(_paramName));
    }
    /**
     * Extracts the given parameter from the Bundle. Throws error if the bundle is null.
     * If there is no boolean value in the bundle with the given key, returns false.
     */
    public static boolean extractBooleanParam(Bundle bundle, String paramName, String tag, String msg)
    {
        return extractParamCore(bundle, paramName, tag, msg, Bundle::getBoolean);
    }
    private static <T> T extractParamCore(Bundle bundle, String paramName, String tag, String msg, Func_2<Bundle, String, T> bundleGetFunc)
    {
        if (bundle == null)
        {
            Utils.error(msg, tag);
            return null; // unreachable
        }

        T param = bundleGetFunc.run(bundle, paramName);
        if (param == null)
            Utils.error(msg, tag);

        return param;
    }
    //endregion

    //region Disable/Enable Controls
    public static void disableControls(ViewGroup vg)
    {
        disableOrEnableControls(vg, /*enable*/ false);
    }
    public static void enableControls(ViewGroup vg)
    {
        disableOrEnableControls(vg, /*enable*/ true);
    }
    private static void disableOrEnableControls(ViewGroup vg, boolean enable)
    {
        if (vg == null)
            return;

        for (int i = 0; i < vg.getChildCount(); i++)
        {
            View child = vg.getChildAt(i);
            if (child instanceof ViewGroup)
                disableOrEnableControls((ViewGroup) child, enable);
            else
                child.setEnabled(enable);
        }
    }
    //endregion

    //region alert
    public static void alert(Context context, String title, String message)
    {
        alert(context, title, message, (dialog, which) -> {/*do nothing*/}, null, null);
    }
    public static void alert(Context context, String title, String message, DialogInterface.OnClickListener neutralClick)
    {
        neutralClick = neutralClick == null
            ? (dialog, which) -> {/*do nothing*/}
            : neutralClick;

        alert(context, title, message, neutralClick, null, null);
    }
    public static void alert(Context context, String title, String message,
                             DialogInterface.OnClickListener negativeClick, DialogInterface.OnClickListener positiveClick)
    {
        negativeClick = negativeClick == null
            ? (dialog, which) -> {/*do nothing*/}
            : negativeClick;
        positiveClick = positiveClick == null
            ? (dialog, which) -> {/*do nothing*/}
            : positiveClick;

        alert(context, title, message, null, negativeClick, positiveClick);
    }
    private static void alert(Context context, String title, String message,
                              DialogInterface.OnClickListener neutralClick,
                              DialogInterface.OnClickListener negativeClick,
                              DialogInterface.OnClickListener positiveClick)
    {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setIcon(android.R.drawable.ic_dialog_alert);

        if (neutralClick != null)
            alertBuilder.setNeutralButton(R.string.ok, neutralClick);
        if (negativeClick != null)
            alertBuilder.setNegativeButton(R.string.no, negativeClick);
        if (positiveClick != null)
            alertBuilder.setPositiveButton(R.string.yes, positiveClick);

        alertBuilder.show();
    }
    //endregion

    public static void refreshActivity(Activity activity)
    {
        activity.finish();
        activity.startActivity(activity.getIntent());
    }
}
