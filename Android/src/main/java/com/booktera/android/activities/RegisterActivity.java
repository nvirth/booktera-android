package com.booktera.android.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.booktera.android.R;
import com.booktera.android.activities.base.ActionBarActivity;
import com.booktera.android.activities.base.AuthenticationActivityBase;
import com.booktera.android.common.Config;
import com.booktera.android.common.Constants;
import com.booktera.android.common.UserData;
import com.booktera.android.common.utils.Utils;
import com.booktera.androidclientproxy.lib.proxy.Services;

import java.util.Date;
import java.util.Random;

public class RegisterActivity extends AuthenticationActivityBase
{
    protected ViewHolder vh;

    class ViewHolder
    {
        public ViewGroup root;
        public EditText userName;
        public EditText email;
        public EditText password;
        public EditText confirmPassword;
        public Button registerBtn;

        public ViewHolder(Activity activity)
        {
            root = (ViewGroup) activity.findViewById(R.id.register_root);
            userName = (EditText) activity.findViewById(R.id.register_userName_value);
            email = (EditText) activity.findViewById(R.id.register_email_value);
            password = (EditText) activity.findViewById(R.id.register_password_value);
            confirmPassword = (EditText) activity.findViewById(R.id.register_confirmPassword_value);
            registerBtn = (Button) activity.findViewById(R.id.register_registerBtn);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        vh = new ViewHolder(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            vh.userName.setText(bundle.getString(Constants.PARAM_USER_NAME));
            vh.password.setText(bundle.getString(Constants.PARAM_PASSWORD));
        }

        vh.registerBtn.setOnClickListener(v -> {
            String userName = vh.userName.getText().toString();
            String email = vh.email.getText().toString();
            String password = vh.password.getText().toString();
            String confirmPassword = vh.confirmPassword.getText().toString();

            if (Utils.isNullOrEmpty(userName) || Utils.isNullOrEmpty(email)
                || Utils.isNullOrEmpty(password) || Utils.isNullOrEmpty(confirmPassword))
            {
                alert(getString(R.string.Error_), getString(R.string.None_of_the_input_fields_may_be_empty));
                return;
            }
            if (!password.equals(confirmPassword))
            {
                alert(getString(R.string.Error_), getString(R.string.The_password_and_the_confirm_password_aren_t_equal));
                return;
            }

            Utils.disableOrEnableControls(vh.root,/*enable*/ false);

            Services.Registration.register(userName, email, password, confirmPassword,
                /*Success*/
                () -> runOnUiThread(() ->
                    alert(getString(R.string.Successful_registration),
                        getString(R.string.The_system_will_now_automatically_log_you_in),
                        /*afterOkClicked*/(dialog, which) -> {
                            setResult(RESULT_OK);
                            doLogin(userName, password, vh.root);
                        })),
                /*Failure*/
                httpResponse -> runOnUiThread(() -> {
                    if (httpResponse == null)
                        alert(getString(R.string.Error_), getString(com.booktera.androidclientproxy.lib.R.string.default_connection_error_msg));
                    else
                        alert(getString(R.string.Error_), getString(R.string.Unsuccessful_registration_error_msg));
                    Utils.disableOrEnableControls(vh.root,/*enable*/ true);
                }));
        });

        if (Config.IsDebug)
        {
            String password = Utils.ifNullOrEmpty(vh.password.getText().toString(), "asdqwe123");
            vh.password.setText(password);
            vh.confirmPassword.setText(password);

            String userName = Utils.ifNullOrEmpty(vh.userName.getText().toString(), "test");
            userName += new Random(new Date().getTime()).nextInt(123456);
            vh.userName.setText(userName);

            vh.email.setText(userName + "@test.com");
        }
    }
}
