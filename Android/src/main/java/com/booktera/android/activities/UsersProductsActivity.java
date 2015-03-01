package com.booktera.android.activities;

import android.os.Bundle;
import com.booktera.android.R;
import com.booktera.android.activities.base.UnAuthorizedActivity;
import com.booktera.android.common.Constants;
import com.booktera.android.fragments.bookBlock.UsersProductsFragment;

public class UsersProductsActivity extends UnAuthorizedActivity
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
