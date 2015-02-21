package com.booktera.androidclientproxy.lib.enums;

import com.google.gson.annotations.SerializedName;

public enum ParticipantType
{
    @SerializedName("0")
    None,

    @SerializedName("1")
    Customer,

    @SerializedName("2")
    Vendor
}

