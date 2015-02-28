package com.booktera.android.activities.base;

import com.booktera.android.common.UserData;

/**
 * This kind of Activity can't be reached in unauthorized mode. So, if the user logged in, came to
 * this page, went on; then logged out, we will go back in history (recursively) to the first
 * un-AuthorizedActivity page
 */
public abstract class AuthorizedActivity extends ActionBarActivity
{

    @Override
    protected void onResume()
    {
        super.onResume();

        goBackIfUnauthorized();
    }

    public void goBackIfUnauthorized()
    {
        //TODO test with auth pages: goBackIfUnauthorized()
        if (!UserData.Instace.isAuthenticated())
            finish();
    }
}
