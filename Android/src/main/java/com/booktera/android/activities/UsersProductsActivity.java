package com.booktera.android.activities;

import android.os.Bundle;
import android.util.Log;
import com.booktera.android.R;
import com.booktera.android.activities.base.ActionBarActivity;
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

        runInFragmentTransaction(
            transaction -> transaction.replace(
                R.id.fragmentContainer,
                UsersProductsFragment.newInstance(extractUserFU())
            ));
    }
    private String extractUserFU()
    {
        String noUserFUmsg = "You can't call this activity without passing this parameter: userFU";
        Bundle bundle = getIntent().getExtras();
        if(bundle == null)
            Utils.error(noUserFUmsg, tag);
        String userFU = bundle.getString(UsersProductsFragment.PARAM_USER_FU);
        if(userFU == null)
            Utils.error(noUserFUmsg, tag);
        return userFU;
    }
}
