package com.booktera.android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.booktera.android.R;
import com.booktera.android.common.Constants;
import com.booktera.android.common.bookBlock.BookBlock;
import com.booktera.android.common.bookBlock.BookBlockDataHolder;
import com.booktera.android.common.models.ProductGroupDetailsVM;
import com.booktera.android.fragments.base.BaseFragment;
import com.booktera.androidclientproxy.lib.enums.BookBlockType;
import com.booktera.androidclientproxy.lib.models.ProductModels.BookRowPLVM;
import com.booktera.androidclientproxy.lib.models.ProductModels.InBookBlockPVM;

import java.util.ArrayList;

public class ProductGroupDetails_StatisticsFragment extends BaseFragment
{
    public static ProductGroupDetails_StatisticsFragment newInstance(String productGroupFU)
    {
        Bundle args = new Bundle();
        args.putString(Constants.PARAM_PRODUCT_GROUP_FU, productGroupFU);

        ProductGroupDetails_StatisticsFragment fragment = new ProductGroupDetails_StatisticsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    // --

    protected class ViewHolder
    {
        public TextView publisher;
        public TextView authors;
        public TextView category;
        public TextView numOfActiveBooks;
        public TextView numOfPassiveBooks;
        public TextView numOfFinishedTransactions;

        public ViewHolder(View view)
        {
            publisher = (TextView) view.findViewById(R.id.productGroupDetails_statistics_publisher_value);
            authors = (TextView) view.findViewById(R.id.productGroupDetails_statistics_authors_value);
            category = (TextView) view.findViewById(R.id.productGroupDetails_statistics_category_value);
            numOfActiveBooks = (TextView) view.findViewById(R.id.productGroupDetails_statistics_numOfActiveBooks_value);
            numOfPassiveBooks = (TextView) view.findViewById(R.id.productGroupDetails_statistics_numOfPassiveBooks_value);
            numOfFinishedTransactions = (TextView) view.findViewById(R.id.productGroupDetails_statistics_numOfFinishedTransactions_value);
        }
    }

    // --

    protected String productGroupFU;
    protected ViewHolder vh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_pgdetails_statistics, container, false);
        vh = new ViewHolder(view);

        productGroupFU = extractParam(Constants.PARAM_PRODUCT_GROUP_FU);
        BookRowPLVM.ProductGroupVM statistics = ProductGroupDetailsVM.Instance.getStatistics(productGroupFU);

        vh.publisher.setText(statistics.getPublisherName());
        vh.authors.setText(statistics.getAuthorNames());
        vh.category.setText(statistics.getCategoryFullPath());
        vh.numOfActiveBooks.setText(String.valueOf(statistics.getSumOfActiveProductsInGroup()));
        vh.numOfPassiveBooks.setText(String.valueOf(statistics.getSumOfPassiveProductsInGroup()));
        vh.numOfFinishedTransactions.setText(String.valueOf(statistics.getSumOfBuys()));

        return view;
    }
}

