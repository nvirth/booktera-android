package com.booktera.android.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import com.booktera.android.R;
import com.booktera.android.activities.base.ActionBarActivity;
import com.booktera.android.common.BookteraFragmentPagerAdapterBase;
import com.booktera.android.common.Config;
import com.booktera.android.common.UserData;
import com.booktera.android.common.utils.Utils;
import com.booktera.android.fragments.LoremIpsumFragment;
import com.booktera.android.fragments.bookBlock.MainHighlightedsFragment;
import com.booktera.android.fragments.bookBlock.NewestsFragment;
import com.booktera.androidclientproxy.lib.proxy.Services;

public class LoginActivity extends ActionBarActivity
{
    protected ViewHolder vh;

    class ViewHolder
    {
        public EditText userName;
        public EditText password;
        public Button loginBtn;
        public Button registerBtn;
        public ViewGroup root;

        public ViewHolder(Activity activity)
        {
            userName = (EditText) activity.findViewById(R.id.login_userName_value);
            password = (EditText) activity.findViewById(R.id.login_password_value);
            loginBtn = (Button) activity.findViewById(R.id.login_loginBtn);
            registerBtn = (Button) activity.findViewById(R.id.login_registerBtn);
            root = (ViewGroup) activity.findViewById(R.id.login_root);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        vh = new ViewHolder(this);

        vh.loginBtn.setOnClickListener(v -> {
            if (Utils.isNullOrEmpty(vh.userName.getText()) || Utils.isNullOrEmpty(vh.password.getText()))
            {
                alert(getString(R.string.Error_), getString(R.string.UserName_and_or_password_mustn_t_be_empty));
                return;
            }

            Utils.disableOrEnableControls(vh.root,/*enable*/ false);

            String userName = vh.userName.getText().toString();
            Services.Authentication.login(
                userName,
                vh.password.getText().toString(),
                /*persistent*/ false,
                (wasSuccessful, userId) -> runOnUiThread(() -> {
                    if (wasSuccessful)
                    {
                        UserData.Instace.setAuthenticated(true);
                        UserData.Instace.setUserName(userName);
                        UserData.Instace.setUserId(userId);

                        showToast(getString(R.string.Successful_login));

                        finish();
                    }
                    else
                    {
                        alert(getString(R.string.Error_), getString(R.string.Wrong_userName_and_or_password));
                        Utils.disableOrEnableControls(vh.root,/*enable*/ true);
                    }
                }));
        });
        vh.registerBtn.setOnClickListener(v -> {
            //todo implement the register button
            showToast("The registerBtn menu item is not implemented yet. ");
            return;
        });

        if (Config.IsDebug)
        {
            vh.userName.setText("ZomiDudu");
            vh.password.setText("asdqwe123");
        }
    }

}
