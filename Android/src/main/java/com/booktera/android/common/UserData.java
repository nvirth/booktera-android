package com.booktera.android.common;

import com.booktera.android.common.utils.Utils;

public class UserData
{
    public static UserData Instace = new UserData();

    private UserData()
    {
    }

    //--

    private boolean isAuthenticated = false;
    private String userNameLowerCase;
    private String userName;
    private int userId;

    // --

    public boolean isAuthenticated()
    {
        return isAuthenticated;
    }
    public void setAuthenticated(boolean isAuthenticated)
    {
        this.isAuthenticated = isAuthenticated;
    }
    public String getUserName()
    {
        return userName;
    }
    public String getUserNameLowerCase()
    {
        if (userNameLowerCase == null)
            userNameLowerCase = Utils.ifNull(userName, "").toLowerCase();

        return userNameLowerCase;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    public int getUserId()
    {
        return userId;
    }
    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    // --

    public void logOut()
    {
        setAuthenticated(false);
        setUserName(null);
        setUserId(0);
    }

}