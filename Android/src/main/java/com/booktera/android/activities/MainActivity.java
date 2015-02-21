package com.booktera.android.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.booktera.android.BookteraApplication;
import com.booktera.android.R;
import com.booktera.android.activities.base.ActionBarActivity;
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
        setContentView(R.layout.activity_main);

        ViewPager mainPager = (ViewPager) findViewById(R.id.mainPager);
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mainPager.setAdapter(mainPagerAdapter);
    }

    public static class MainPagerAdapter extends FragmentPagerAdapter
    {
        // Alias
        public static Resources r = BookteraApplication.getAppResources();

        public String[] headers = {
            r.getString(R.string.main_header_highlighteds),
            r.getString(R.string.main_header_newests),
            r.getString(R.string.main_header_introduction)
        };

        public MainPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return headers[position];
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

        @Override
        public int getCount()
        {
            return headers.length;
        }
    }
}
