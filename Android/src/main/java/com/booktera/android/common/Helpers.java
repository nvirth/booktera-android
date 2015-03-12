package com.booktera.android.common;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import com.booktera.android.BookteraApplication;
import com.booktera.android.activities.UsersProductsActivity;

/**
 * Created by Norbert on 2015.03.12..
 */
public class Helpers
{
    public static MenuItem.OnMenuItemClickListener gotoUsersProducts(String userFriendlyUrl, Context context)
    {
        return item -> {
            Intent intent = new Intent(BookteraApplication.getAppContext(), UsersProductsActivity.class);
            intent.putExtra(Constants.PARAM_USER_FU, userFriendlyUrl);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
            return true;
        };
    }
}
