package com.booktera.android.activities.base;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import com.booktera.android.R;
import com.booktera.android.activities.*;
import com.booktera.android.common.UserData;
import com.booktera.android.common.utils.Utils;
import com.booktera.androidclientproxy.lib.proxy.Services;

/**
 * Handles the ActionBar for all the Activities.
 * All of the Activities derive from this, because all of them have ActionBar
 */
abstract class ActionBarActivity extends BaseActivity
{
    private Boolean _wasAuthenticated;

    private boolean authLevelChanged()
    {
        return _wasAuthenticated != null && _wasAuthenticated != UserData.Instace.isAuthenticated();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        _wasAuthenticated = UserData.Instace.isAuthenticated();

        int menuTemplateId = _wasAuthenticated
            ? R.menu.authorized
            : R.menu.unauthorized;

        getMenuInflater().inflate(menuTemplateId, menu);
        return true;
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        refreshMenuIfNeeded();
    }

    private void refreshMenuIfNeeded()
    {
        if (authLevelChanged())
            invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO remove the default comment from onOptionsItemSelected after understanding it...
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId())
        {
            case R.id.action_search:
                onSearchClicked();
                return true;
            case R.id.action_cart:
                onCartClicked();
                return true;
            case R.id.action_categories:
                onCategoriesClicked();
                return true;
            case R.id.action_profile:
                onProfileClicked();
                return true;
            case R.id.action_sell:
                onSellClicked();
                return true;
            case R.id.action_logout:
                onLogoutClicked();
                return true;
            case R.id.action_login:
                onLoginClicked();
                return true;
            case R.id.action_register:
                onRegisterClicked();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //TODO implement ActionBar clicks

    private void onCartClicked()
    {
        showToast("The Cart menu item is not implemented yet. ");
    }
    private void onProfileClicked()
    {
        startActivity(new Intent(this, ProfileActivity.class));
    }
    private void onSellClicked()
    {
        showToast("The Sell menu item is not implemented yet. ");
    }
    private void onLogoutClicked()
    {
        Services.Authentication.logout(
            () -> runOnUiThread(() -> {
                    UserData.Instace.setAuthenticated(false);
                    UserData.Instace.setUserName(null);
                    UserData.Instace.setUserId(0);

                    showToast(getString(R.string.Successfully_logged_out_));

                    // Step back one, if it's an auth required page.
                    // This step will start a chain back-process, and also refresh the menu
                    if (this instanceof AuthorizedActivity)
                        ((AuthorizedActivity) this).goBackIfUnauthorized(); // == finish();
                    else
                        refreshMenuIfNeeded();
                }
            ));
    }
    private void onSearchClicked()
    {
        startActivity(new Intent(this, SearchActivity.class));
    }
    private void onCategoriesClicked()
    {
        startActivity(new Intent(this, CategoryActivity.class));
    }
    private void onLoginClicked()
    {
        startActivity(new Intent(this, LoginActivity.class));
    }
    private void onRegisterClicked()
    {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
