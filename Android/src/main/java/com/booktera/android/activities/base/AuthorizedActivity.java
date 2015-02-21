package com.booktera.android.activities.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.booktera.android.R;

/**
 * This kind of Activity can't be reached in unauthorized mode. So, if the user logged in, came to
 * this page, went on; then logged out, we will go back in history (recursively) to the first
 * un-AuthorizedActivity page
 */
public class AuthorizedActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO if the user logged out, and we come here, go back
    }
}
