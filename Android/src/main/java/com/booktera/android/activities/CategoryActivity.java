package com.booktera.android.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.booktera.android.R;
import com.booktera.android.activities.base.ActionBarActivity;
import com.booktera.android.common.BookteraFragmentPagerAdapterBase;
import com.booktera.android.fragments.bookBlock.CategoriesFragment;
import com.booktera.android.fragments.bookBlock.MainHighlightedsFragment;
import com.booktera.android.fragments.bookBlock.ProductsInCategoryFragment;
import com.booktera.androidclientproxy.lib.models.EntityFramework.Category;

public class CategoryActivity extends ActionBarActivity
{
    public static final String PARAM_CATEGORY_FU = "PARAM_CATEGORY_FRIENDLY_URL";
    FragmentPagerAdapter categoryPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager);

        categoryPagerAdapter = new CategoryPagerAdapter(getIntent().getExtras(), getSupportFragmentManager());
        ViewPager mainPager = (ViewPager) findViewById(R.id.pagerPager);
        mainPager.setAdapter(categoryPagerAdapter);
    }

    public static class CategoryPagerAdapter extends BookteraFragmentPagerAdapterBase
    {
        private String categoryFriendlyUrl;

        public CategoryPagerAdapter(Bundle data, FragmentManager fm)
        {
            super(fm);

            if (data != null)
                categoryFriendlyUrl = data.getString(PARAM_CATEGORY_FU);
        }

        @Override
        protected void initHeaders()
        {
            headers = new String[]{
                r.getString(R.string.category_header_subcategories),
                r.getString(R.string.category_header_books)
            };
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position)
            {
                case 0:
                    return CategoriesFragment.newInstance(categoryFriendlyUrl);
                case 1:
                    return ProductsInCategoryFragment.newInstance(categoryFriendlyUrl);
                default:
                    return null;
            }
        }
    }
}
