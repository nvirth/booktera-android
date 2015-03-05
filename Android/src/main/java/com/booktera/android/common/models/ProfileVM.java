package com.booktera.android.common.models;


import com.booktera.androidclientproxy.lib.models.UserProfileEditVM;

/**
 * Created by Norbert on 2015.02.09..
 */
public class ProfileVM
{
    public static ProfileVM SavedInstance = new ProfileVM();
    private static ProfileVM EditableInstance;

    public static ProfileVM getEditableInstance()
    {
        return getEditableInstance(false);
    }
    public static ProfileVM getEditableInstance(boolean forceRefresh)
    {
        if (EditableInstance != null && !forceRefresh)
            return EditableInstance;

        refreshEditableInstance();
        return EditableInstance;
    }
    public static void refreshEditableInstance()
    {
        EditableInstance = new ProfileVM();
        EditableInstance.setUserProfile(new UserProfileEditVM(SavedInstance.getUserProfile()));
    }

    public static void applyChangesToSavedInstance()
    {
        SavedInstance = new ProfileVM();
        SavedInstance.setUserProfile(new UserProfileEditVM(EditableInstance.getUserProfile()));
    }

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
