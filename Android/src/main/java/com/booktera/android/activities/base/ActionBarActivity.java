package com.booktera.android.activities.base;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import com.booktera.android.R;
import com.booktera.android.activities.CategoryActivity;
import com.booktera.android.activities.SearchActivity;
import com.booktera.android.common.UserData;

/**
 * Handles the ActionBar for all the Activities
 */
public abstract class ActionBarActivity extends BaseActivity
{

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        int menuTemplateId = UserData.Instace.isAuthenticated()
            ? R.menu.authorized
            : R.menu.unauthorized;

        getMenuInflater().inflate(menuTemplateId, menu);
        return true;
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
        showToast("The Profile menu item is not implemented yet. ");
    }
    private void onSellClicked()
    {
        showToast("The Sell menu item is not implemented yet. ");
    }
    private void onLogoutClicked()
    {
        showToast("The Logout menu item is not implemented yet. ");
    }
    private void onSearchClicked()
    {
        Intent gotoSearchIntent = new Intent(this, SearchActivity.class);
        startActivity(gotoSearchIntent);
    }
    private void onCategoriesClicked()
    {
        Intent gotoCategorioesIntent = new Intent(this, CategoryActivity.class);
        startActivity(gotoCategorioesIntent);
    }
    private void onLoginClicked()
    {
        showToast("The Login menu item is not implemented yet. ");
    }
    private void onRegisterClicked()
    {
        showToast("The Register menu item is not implemented yet. ");
    }
}
