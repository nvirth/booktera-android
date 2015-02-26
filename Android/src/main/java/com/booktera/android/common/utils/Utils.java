package com.booktera.android.common.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;
import com.booktera.android.BookteraApplication;
import com.booktera.android.R;
import com.booktera.android.common.Config;
import com.booktera.androidclientproxy.lib.models.ProductModels.InBookBlockPVM;
import com.booktera.androidclientproxy.lib.utils.Action_1;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

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
        if (str == null || str.equals(""))
            return ifNullValue;
        return str;
    }
    public static boolean isNullOrEmpty(CharSequence str)
    {
        return str == null || str.equals("");
    }

    //endregion

    //region setImage

    public static void setProductImage(InBookBlockPVM inBookBlockPVM, ImageView imageView)
    {
        setImage(inBookBlockPVM, imageView, Config.ProductImagesUrlDirFormat);
    }
    public static void setUserImage(InBookBlockPVM inBookBlockPVM, ImageView imageView)
    {
        setImage(inBookBlockPVM, imageView, Config.UserImagesUrlDirFormat);
    }
    private static void setImage(InBookBlockPVM inBookBlockPVM, ImageView imageView, String imageDirUrlFormat)
    {
        String url = null;
        try
        {
            String imageFile = Utils.ifNullOrEmpty(
                inBookBlockPVM.getProduct().getImageUrl(),
                inBookBlockPVM.getProductGroup().getImageUrl()
            );
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

    /**
     * Logs the msg as error, then throws a RuntimeException with it
     */
    public static void error(String msg, String tag)
    {
        com.booktera.androidclientproxy.lib.utils.Utils.error(msg, tag);
    }
}
