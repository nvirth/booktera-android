package com.booktera.androidclientproxy.lib.models;

public class UserAddressVM
{

    private String ZipCode;
    public String getZipCode()
    {
        return ZipCode;
    }

    public void setZipCode(String value)
    {
        ZipCode = value;
    }

    private String City;
    public String getCity()
    {
        return City;
    }

    public void setCity(String value)
    {
        City = value;
    }

    private String StreetAndHouseNumber;
    public String getStreetAndHouseNumber()
    {
        return StreetAndHouseNumber;
    }

    public void setStreetAndHouseNumber(String value)
    {
        StreetAndHouseNumber = value;
    }

    private String Country;
    public String getCountry()
    {
        return Country;
    }

    public void setCountry(String value)
    {
        Country = value;
    }

    private int Id;
    public int getId()
    {
        return Id;
    }

    public void setId(int value)
    {
        Id = value;
    }

    private boolean IsDefault;
    public boolean getIsDefault()
    {
        return IsDefault;
    }

    public void setIsDefault(boolean value)
    {
        IsDefault = value;
    }

}