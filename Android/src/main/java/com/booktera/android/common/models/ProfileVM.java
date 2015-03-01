package com.booktera.android.common.models;


import com.booktera.androidclientproxy.lib.models.UserProfileEditVM;

/**
 * Created by Norbert on 2015.02.09..
 */
public class ProfileVM
{
    public static ProfileVM Instance = new ProfileVM();

    protected ProfileVM()
    {
    }

    // --

    private UserProfileEditVM userProfile;

    public UserProfileEditVM getUserProfile()
    {
        return userProfile;
    }
    public void setUserProfile(UserProfileEditVM userProfile)
    {
        this.userProfile = userProfile;
    }
}
