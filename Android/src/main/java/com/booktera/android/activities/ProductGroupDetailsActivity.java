package com.booktera.android.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.booktera.android.R;
import com.booktera.android.activities.base.UnAuthorizedActivity;
import com.booktera.android.common.BookteraFragmentPagerAdapterBase;
import com.booktera.android.common.Constants;
import com.booktera.android.common.models.ProductGroupDetailsVM;
import com.booktera.android.fragments.ProductGroupDetails_DetailsFragment;
import com.booktera.android.fragments.ProductGroupDetails_StatisticsFragment;
import com.booktera.android.fragments.bookBlock.ProductGroupDetails_BooksFragment;
import com.booktera.androidclientproxy.lib.models.ProductModels.BookRowPLVM;
import com.booktera.androidclientproxy.lib.proxy.Services;

public class ProductGroupDetailsActivity extends UnAuthorizedActivity
{
    FragmentPagerAdapter pgPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager);

        String productGroupFU = extractParam(Constants.PARAM_PRODUCT_GROUP_FU);
        loadData(productGroupFU);
    }

    private void loadData(String productGroupFU)
    {
        BookRowPLVM pgDetails = ProductGroupDetailsVM.Instance.getProductGroupDetails(productGroupFU);
        if (pgDetails != null)
            applyData(productGroupFU, pgDetails);
        else
            Services.ProductGroupManager.getFullDetailed(productGroupFU, 1, 100,
                bookRowPLVM -> runOnUiThread(() -> {
                    ProductGroupDetailsVM.Instance.setProductGroupDetails(productGroupFU, bookRowPLVM);
                    applyData(productGroupFU, bookRowPLVM);
                })
                , null);
    }

    private void applyData(String productGroupFU, BookRowPLVM bookRowPLVM)
    {
        pgPagerAdapter = new PgPagerAdapter(productGroupFU, getSupportFragmentManager());
        ViewPager mainPager = (ViewPager) findViewById(R.id.pagerPager);
        mainPager.setAdapter(pgPagerAdapter);

        String title = String.format(
            getString(R.string.title_activity_productGroupDetails_Format),
            bookRowPLVM.getProductGroup().getTitle()
        );
        setTitle(title);
    }

    public static class PgPagerAdapter extends BookteraFragmentPagerAdapterBase
    {
        private String productGroupFU;

        public PgPagerAdapter(String productGroupFU, FragmentManager fm)
        {
            super(fm);

            this.productGroupFU = productGroupFU;
        }

        @Override
        protected void initHeaders()
        {
            headers = new String[]{
                r.getString(R.string.productGroupDetails_header_details),
                r.getString(R.string.productGroupDetails_header_books),
                r.getString(R.string.productGroupDetails_header_statistics)
            };
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position)
            {
                case 0:
                    return ProductGroupDetails_DetailsFragment.newInstance(productGroupFU);
                case 1:
                    return ProductGroupDetails_BooksFragment.newInstance(productGroupFU);
                case 2:
                    return ProductGroupDetails_StatisticsFragment.newInstance(productGroupFU);
                default:
                    return null;
            }
        }
    }
}
