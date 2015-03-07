package com.booktera.android.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.booktera.android.R;
import com.booktera.android.activities.base.AuthorizedActivity;
import com.booktera.android.common.BookteraFragmentPagerAdapterBase;
import com.booktera.android.fragments.userOrder.UserOrderFragment;
import com.booktera.androidclientproxy.lib.enums.TransactionType;

public class SellsActivity extends AuthorizedActivity
{
    FragmentPagerAdapter sellsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager);

        sellsPagerAdapter = new SellsPagerAdapter(getSupportFragmentManager());
        ViewPager mainPager = (ViewPager) findViewById(R.id.pagerPager);
        mainPager.setAdapter(sellsPagerAdapter);
    }

    public static class SellsPagerAdapter extends BookteraFragmentPagerAdapterBase
    {
        public SellsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        protected void initHeaders()
        {
            headers = new String[]{
                r.getString(R.string.sell_header_in_others_cart),
                r.getString(R.string.sell_header_in_progress),
                r.getString(R.string.sell_header_finished)
            };
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position)
            {
                case 0:
                    return UserOrderFragment.newInstance(TransactionType.InCartsByOthers);
                case 1:
                    return UserOrderFragment.newInstance(TransactionType.InProgressSells);
                case 2:
                    return UserOrderFragment.newInstance(TransactionType.EarlierSells);
                default:
                    return null;
            }
        }
    }
}
