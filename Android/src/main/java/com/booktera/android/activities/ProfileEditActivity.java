package com.booktera.android.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.booktera.android.R;
import com.booktera.android.activities.base.AuthorizedActivity;
import com.booktera.android.common.models.ProfileVM;
import com.booktera.android.common.utils.Utils;
import com.booktera.androidclientproxy.lib.models.UserProfileEditVM;
import com.booktera.androidclientproxy.lib.proxy.Services;

public class ProfileEditActivity extends AuthorizedActivity
{
    protected ViewHolder vh;

    class ViewHolder
    {
        public ViewGroup root;
        public EditText fullName;
        public EditText email;
        public EditText phoneNumber;
        public Button saveBtn;

        public ViewHolder(Activity activity)
        {
            root = (ViewGroup) activity.findViewById(R.id.profileEdit_root);
            fullName = (EditText) activity.findViewById(R.id.profileEdit_fullName);
            email = (EditText) activity.findViewById(R.id.profileEdit_email);
            phoneNumber = (EditText) activity.findViewById(R.id.profileEdit_phoneNumber);
            saveBtn = (Button) activity.findViewById(R.id.profileEdit_saveBtn);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        vh = new ViewHolder(this);

        vh.saveBtn.setOnClickListener(v -> {
            Utils.disableControls(vh.root);
            UserProfileEditVM userProfile = ProfileVM.getEditableInstance().getUserProfile();
            updateUserProfileInstance(userProfile);

            Services.UserProfileManager.update(
                userProfile,
                () -> runOnUiThread(() -> {
                    // Apply the changes locally (cache)
                    ProfileVM.applyChangesToSavedInstance();

                    showToast(getString(R.string.Profile_modified_successfully));
                    finish();
                }),
                httpResponse -> runOnUiThread(() -> {
                    if (httpResponse != null)
                        showToast(getString(R.string.profileEdit_save_failureErrorMessage), /*isLong*/ true);

                    Utils.enableControls(vh.root);
                })
            );
        });

        loadData();
    }
    private void updateUserProfileInstance(UserProfileEditVM userProfile)
    {
        userProfile.setPhoneNumber(vh.phoneNumber.getText().toString());
        userProfile.setEMail(vh.email.getText().toString());
        userProfile.setFullName(vh.fullName.getText().toString());
    }

    private void loadData()
    {
        UserProfileEditVM userProfile = ProfileVM.getEditableInstance().getUserProfile();
        applyData(userProfile);
    }

    private void applyData(UserProfileEditVM userProfile)
    {
        vh.fullName.setText(userProfile.getFullName());
        vh.email.setText(userProfile.getEMail());
        vh.phoneNumber.setText(userProfile.getPhoneNumber());
    }
}
