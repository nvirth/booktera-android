package com.booktera.android.common;

import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.booktera.android.BookteraApplication;

//Created by Norbert on 2015.02.22..

/**
 * To use it:
 * <ul>
 *     <li>Implement method: {@link #initHeaders()}</li>
 *     <li>Implement method: {@link #getItem(int)}</li>
 * </ul>
 */
public abstract class BookteraFragmentPagerAdapterBase extends FragmentPagerAdapter
{
    /**
     * Alias
     */
    protected static Resources r = BookteraApplication.getAppResources();

    protected String[] headers;
    protected abstract void initHeaders();

    public BookteraFragmentPagerAdapterBase(FragmentManager fm)
    {
        super(fm);
        initHeaders();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return headers[position];
    }

    @Override
    public int getCount()
    {
        return headers.length;
    }
}