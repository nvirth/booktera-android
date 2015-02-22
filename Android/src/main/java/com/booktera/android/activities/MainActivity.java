package com.booktera.android.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;
import com.booktera.android.BookteraApplication;
import com.booktera.android.R;
import com.booktera.android.activities.base.ActionBarActivity;
import com.booktera.android.common.BookteraFragmentPagerAdapterBase;
import com.booktera.android.fragments.LoremIpsumFragment;
import com.booktera.android.fragments.bookBlock.MainHighlightedsFragment;
import com.booktera.android.fragments.bookBlock.NewestsFragment;

public class MainActivity extends ActionBarActivity
{
    FragmentPagerAdapter mainPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.pagerLayout);
        layout.setBackgroundResource(R.drawable.background_light);

        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        ViewPager mainPager = (ViewPager) findViewById(R.id.pagerPager);
        mainPager.setAdapter(mainPagerAdapter);
    }

    public static class MainPagerAdapter extends BookteraFragmentPagerAdapterBase
    {
        public MainPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        protected void initHeaders()
        {
            headers = new String[]{
                r.getString(R.string.main_header_highlighteds),
                r.getString(R.string.main_header_newests),
                r.getString(R.string.main_header_introduction)
            };
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position)
            {
                case 0:
                    return new MainHighlightedsFragment();
                case 1:
                    return new NewestsFragment();
                case 2:
                    return new LoremIpsumFragment();
                default:
                    return null;
            }
        }
    }
}
