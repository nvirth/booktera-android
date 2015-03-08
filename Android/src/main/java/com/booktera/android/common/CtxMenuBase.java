package com.booktera.android.common;

import android.support.annotation.IdRes;
import android.view.ContextMenu;
import android.view.MenuItem;

/**
 * Created by Norbert on 2015.03.08..
 */
public abstract class CtxMenuBase
{
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
    public abstract MenuItem.OnMenuItemClickListener fetchClickListener(MenuItem menuItem);
    public abstract void setupContextMenu();
}
