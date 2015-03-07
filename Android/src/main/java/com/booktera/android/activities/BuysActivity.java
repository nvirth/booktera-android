package com.booktera.android.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.booktera.android.R;
import com.booktera.android.activities.base.AuthorizedActivity;
import com.booktera.android.common.BookteraFragmentPagerAdapterBase;
import com.booktera.android.fragments.LoremIpsumFragment;
import com.booktera.android.fragments.bookBlock.MainHighlightedsFragment;
import com.booktera.android.fragments.bookBlock.NewestsFragment;
import com.booktera.android.fragments.userOrder.UserOrderFragment;
import com.booktera.androidclientproxy.lib.enums.TransactionType;

public class BuysActivity extends AuthorizedActivity
{
    FragmentPagerAdapter buysPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager);

        buysPagerAdapter = new BuysPagerAdapter(getSupportFragmentManager());
        ViewPager mainPager = (ViewPager) findViewById(R.id.pagerPager);
        mainPager.setAdapter(buysPagerAdapter);
    }

    public static class BuysPagerAdapter extends BookteraFragmentPagerAdapterBase
    {
        public BuysPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        protected void initHeaders()
        {
            headers = new String[]{
                r.getString(R.string.buy_header_carts),
                r.getString(R.string.buy_header_in_progress),
                r.getString(R.string.buy_header_finished)
            };
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position)
            {
                case 0:
                    return UserOrderFragment.newInstance(TransactionType.Carts);
                case 1:
                    return UserOrderFragment.newInstance(TransactionType.InProgressBuys);
                case 2:
                    return UserOrderFragment.newInstance(TransactionType.EarlierBuys);
                default:
                    return null;
            }
        }
    }
}
