package com.booktera.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.booktera.android.R;
import com.booktera.android.activities.base.AuthenticationActivityBase;
import com.booktera.android.common.Config;
import com.booktera.android.common.Constants;

public class LoginActivity extends AuthenticationActivityBase
{
    protected ViewHolder vh;
    private static final int dummyRequestCode = 1;

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
            String userName = vh.userName.getText().toString();
            String password = vh.password.getText().toString();
            doLogin(userName, password, vh.root);
        });
        vh.registerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            intent.putExtra(Constants.PARAM_USER_NAME, vh.userName.getText().toString());
            intent.putExtra(Constants.PARAM_PASSWORD, vh.password.getText().toString());

            // We will end up in 'onActivityResult' if the registration was successful
            startActivityForResult(intent, dummyRequestCode);
        });

        if (Config.IsDebug)
        {
            vh.userName.setText("ZomiDudu");
            vh.password.setText("asdqwe123");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // We came back from successful Registration
        if(resultCode == RESULT_OK)
            finish();
    }
}
