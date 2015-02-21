package com.booktera.androidclientproxy.lib.enums;

import com.google.gson.annotations.SerializedName;


public enum UserOrderRatingState
{
    @SerializedName("0")
    Nincs,

    @SerializedName("1")
    DontNeed,

    @SerializedName("2")
    WaitForFeedback,

    @SerializedName("3")
    Good,

    @SerializedName("4")
    Neutral,

    @SerializedName("5")
    Bad
}

