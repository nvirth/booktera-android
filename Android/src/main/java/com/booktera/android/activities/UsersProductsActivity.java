package com.booktera.android.activities;

import android.os.Bundle;
import android.util.Log;
import com.booktera.android.R;
import com.booktera.android.activities.base.ActionBarActivity;
import com.booktera.android.common.Constants;
import com.booktera.android.common.utils.Utils;
import com.booktera.android.fragments.bookBlock.SearchFragment;
import com.booktera.android.fragments.bookBlock.UsersProductsFragment;

public class UsersProductsActivity extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_products);

        String userFU = extractParam(Constants.PARAM_USER_FU);
        runInFragmentTransaction(
            transaction -> transaction.replace(
                R.id.fragmentContainer,
                UsersProductsFragment.newInstance(userFU)
            ));
    }
}
