package com.booktera.android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.booktera.android.R;
import com.booktera.android.common.Constants;
import com.booktera.android.common.bookBlock.BookBlock;
import com.booktera.android.common.models.ProductGroupDetailsVM;
import com.booktera.android.fragments.base.BaseFragment;
import com.booktera.androidclientproxy.lib.models.ProductModels.InBookBlockPVM;

import java.util.ArrayList;

public class ProductGroupDetails_DetailsFragment extends BaseFragment
{
    public static ProductGroupDetails_DetailsFragment newInstance(String productGroupFU)
    {
        Bundle args = new Bundle();
        args.putString(Constants.PARAM_PRODUCT_GROUP_FU, productGroupFU);

        ProductGroupDetails_DetailsFragment fragment = new ProductGroupDetails_DetailsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    // --

    protected class ViewHolder
    {
        public TextView productGroupDescription;

        public ViewHolder(View view)
        {
            productGroupDescription = (TextView) view.findViewById(R.id.product_group_description);
        }
    }

    // --

    protected String productGroupFU;
    protected ViewHolder vh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_pgdetails_details, container, false);
        vh = new ViewHolder(view);

        productGroupFU = extractStringParam(Constants.PARAM_PRODUCT_GROUP_FU);
        InBookBlockPVM details = ProductGroupDetailsVM.Instance.getDetails(productGroupFU);

        //-- Description
        vh.productGroupDescription.setText(details.getProductGroup().getDescription());

        //-- BookBlock
        BookBlock bookBlock = new BookBlock(new BookBlock.CtorArgs(details, view, getActivity()));
        bookBlock.fill();

        return view;
    }
}

