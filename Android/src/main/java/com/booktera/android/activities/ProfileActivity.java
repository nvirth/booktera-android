package com.booktera.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.booktera.android.R;
import com.booktera.android.activities.base.AuthorizedActivity;
import com.booktera.android.common.UserData;
import com.booktera.android.common.models.ProfileVM;
import com.booktera.android.common.utils.Utils;
import com.booktera.androidclientproxy.lib.models.UserProfileEditVM;
import com.booktera.androidclientproxy.lib.proxy.Services;

public class ProfileActivity extends AuthorizedActivity
{
    protected ViewHolder vh;

    class ViewHolder
    {
        public ViewGroup root;
        public ImageView userImage;
        public TextView userName;
        public TextView fullName;
        public TextView email;
        public TextView phoneNumber;
        public Button editBtn;

        public ViewHolder(Activity activity)
        {
            root = (ViewGroup) activity.findViewById(R.id.profile_root);
            userImage = (ImageView) activity.findViewById(R.id.profile_userImage);
            userName = (TextView) activity.findViewById(R.id.profile_userName);
            fullName = (TextView) activity.findViewById(R.id.profile_fullName);
            email = (TextView) activity.findViewById(R.id.profile_email);
            phoneNumber = (TextView) activity.findViewById(R.id.profile_phoneNumber);
            editBtn = (Button) activity.findViewById(R.id.profile_editBtn);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        vh = new ViewHolder(this);

        vh.editBtn.setOnClickListener(v -> {
                ProfileVM.refreshEditableInstance();
                startActivityForResult(new Intent(this, ProfileEditActivity.class), dummyRequestCode);
            }
        );

        loadData();
    }

    private void loadData()
    {
        UserProfileEditVM userProfile = ProfileVM.SavedInstance.getUserProfile();
        if (userProfile != null)
            applyData(userProfile);
        else
            Services.UserProfileManager.getForEdit(
                userProfileEditVM -> runOnUiThread(() -> {
                    ProfileVM.SavedInstance.setUserProfile(userProfileEditVM);
                    applyData(userProfileEditVM);
                }));
    }

    private void applyData(UserProfileEditVM userProfile)
    {
        vh.userName.setText(UserData.Instace.getUserName());
        vh.fullName.setText(userProfile.getFullName());
        vh.email.setText(userProfile.getEMail());
        vh.phoneNumber.setText(userProfile.getPhoneNumber());
        Utils.setUserImage(userProfile.getImageUrl(), vh.userImage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // refresh
        loadData();
    }
}
