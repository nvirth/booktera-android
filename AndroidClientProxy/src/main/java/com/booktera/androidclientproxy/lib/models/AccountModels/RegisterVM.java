package com.booktera.androidclientproxy.lib.models.AccountModels;

import com.booktera.androidclientproxy.lib.models.UserAddressVM;

/**
 * - UserAddress: Default cím, null, ha nincs
 * - AuthorName: Akkor van kitöltve, ha a felhasználó egy szerző; null egyébként
 * - PublisherName: Akkor van kitöltve, ha a felhasználó egy kiadó; null egyébként
 */
public class RegisterVM
{

    // -- Required
    private String UserName;
    public String getUserName()
    {
        return UserName;
    }

    public void setUserName(String value)
    {
        UserName = value;
    }

    private String EMail;
    public String getEMail()
    {
        return EMail;
    }

    public void setEMail(String value)
    {
        EMail = value;
    }

    private String Password;
    public String getPassword()
    {
        return Password;
    }

    public void setPassword(String value)
    {
        Password = value;
    }

    private String ConfirmPassword;
    public String getConfirmPassword()
    {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String value)
    {
        ConfirmPassword = value;
    }

    // -- Not required
    private String FullName;
    public String getFullName()
    {
        return FullName;
    }

    public void setFullName(String value)
    {
        FullName = value;
    }

    private String PhoneNumber;
    public String getPhoneNumber()
    {
        return PhoneNumber;
    }

    public void setPhoneNumber(String value)
    {
        PhoneNumber = value;
    }

    private String ImageUrl;
    public String getImageUrl()
    {
        return ImageUrl;
    }

    public void setImageUrl(String value)
    {
        ImageUrl = value;
    }

    private String AuthorName;
    public String getAuthorName()
    {
        return AuthorName;
    }

    public void setAuthorName(String value)
    {
        AuthorName = value;
    }

    private String PublisherName;
    public String getPublisherName()
    {
        return PublisherName;
    }

    public void setPublisherName(String value)
    {
        PublisherName = value;
    }

    private UserAddressVM UserAddress;
    public UserAddressVM getUserAddress()
    {
        return UserAddress;
    }

    public void setUserAddress(UserAddressVM value)
    {
        UserAddress = value;
    }

}


