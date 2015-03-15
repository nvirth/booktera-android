package com.booktera.android.common;

import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.IdRes;
import android.view.ContextMenu;
import android.view.MenuItem;
import com.booktera.android.BookteraApplication;
import com.booktera.android.R;
import com.booktera.android.common.utils.Utils;
import com.booktera.androidclientproxy.lib.utils.*;
import org.apache.http.HttpResponse;

/**
 * Created by Norbert on 2015.03.08..
 */
public abstract class CtxMenuBase
{
    protected static final Resources r = BookteraApplication.getAppResources();
    protected Activity activity;

    protected CtxMenuBase(Activity activity)
    {
        this.activity = activity;
    }

    public abstract MenuItem.OnMenuItemClickListener fetchClickListener(MenuItem menuItem);
    public abstract void setupContextMenu();

    //region enable
    public void enable(@IdRes int menuItemID, ContextMenu menu)
    {
        enable(menu.findItem(menuItemID));
    }
    public void enable(MenuItem menuItem)
    {
        menuItem.setVisible(true);
        menuItem.setEnabled(true);
        menuItem.setOnMenuItemClickListener(fetchClickListener(menuItem));
    }
    //endregion

    //region handleCtxClick
    protected MenuItem.OnMenuItemClickListener handleCtxClick(String confirmMsg, String successMsg, String errorMsg, Action_2<Action, Action_1<HttpResponse>> modifyOrderAction, Action refreshViewAfterSuccess)
    {
        return item -> {
            handleCtxClick_core(confirmMsg, successMsg, errorMsg, modifyOrderAction, refreshViewAfterSuccess);
            return true;
        };
    }
    protected MenuItem.OnMenuItemClickListener handleCtxClick(Integer intValue, String confirmMsg, String successMsg, String errorMsg, Action_3<Integer, Action, Action_1<HttpResponse>> modifyOrderAction, Action refreshViewAfterSuccess)
    {
        return item -> {
            handleCtxClick_core(intValue, confirmMsg, successMsg, errorMsg, modifyOrderAction, refreshViewAfterSuccess);
            return true;
        };
    }
    protected MenuItem.OnMenuItemClickListener handleCtxClick(Integer intValue1, Integer intValue2, String confirmMsg, String successMsg, String errorMsg, Action_4<Integer, Integer, Action, Action_1<HttpResponse>> modifyOrderAction, Action refreshViewAfterSuccess)
    {
        return item -> {
            handleCtxClick_core(intValue1, intValue2, confirmMsg, successMsg, errorMsg, modifyOrderAction, refreshViewAfterSuccess);
            return true;
        };
    }
    protected void handleCtxClick_core(String confirmMsg, String successMsg, String errorMsg, Action_2<Action, Action_1<HttpResponse>> modifyOrderAction, Action refreshViewAfterSuccess)
    {
        alertConfirmThenRun_core(confirmMsg, () ->
            modifyOrderAction.run(
                () -> /*success*/ activity.runOnUiThread(() -> {
                    Utils.showToast(successMsg,/*isLong*/ true);
                    refreshViewAfterSuccess.run();
                }),
                httpResponse -> /*failure*/ activity.runOnUiThread(() -> {
                        String _title = r.getString(R.string.Error_);
                        Utils.alert(activity, _title, errorMsg);
                    }
                )
            ));
    }
    protected void handleCtxClick_core(Integer intValue, String confirmMsg, String successMsg, String errorMsg, Action_3<Integer, Action, Action_1<HttpResponse>> modifyOrderAction, Action refreshViewAfterSuccess)
    {
        alertConfirmThenRun_core(confirmMsg, () ->
            modifyOrderAction.run(
                intValue,
                () -> /*success*/ activity.runOnUiThread(() -> {
                    Utils.showToast(successMsg,/*isLong*/ true);
                    refreshViewAfterSuccess.run();
                }),
                httpResponse -> /*failure*/ activity.runOnUiThread(() -> {
                        String _title = r.getString(R.string.Error_);
                        Utils.alert(activity, _title, errorMsg);
                    }
                )
            ));
    }
    protected void handleCtxClick_core(Integer intValue1, Integer intValue2, String confirmMsg, String successMsg, String errorMsg, Action_4<Integer, Integer, Action, Action_1<HttpResponse>> modifyOrderAction, Action refreshViewAfterSuccess)
    {
        alertConfirmThenRun_core(confirmMsg, () ->
            modifyOrderAction.run(
                intValue1,
                intValue2,
                () -> /*success*/ activity.runOnUiThread(() -> {
                    Utils.showToast(successMsg,/*isLong*/ true);
                    refreshViewAfterSuccess.run();
                }),
                httpResponse -> /*failure*/ activity.runOnUiThread(() -> {
                        String _title = r.getString(R.string.Error_);
                        Utils.alert(activity, _title, errorMsg);
                    }
                )
            ));
    }
    private MenuItem.OnMenuItemClickListener alertConfirmThenRun(String confirmMsg, Action positiveClickAction)
    {
        return item -> {
            alertConfirmThenRun_core(confirmMsg, positiveClickAction);
            return true;
        };
    }
    private void alertConfirmThenRun_core(String confirmMsg, Action positiveClickAction)
    {
        String title = r.getString(R.string.Confirm);
        Utils.alert(activity, title, confirmMsg, /*negativeClick*/ null,
            (dialog, which) /*positiveClick*/ -> positiveClickAction.run()
        );
    }
    //endregion

}
