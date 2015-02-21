package com.booktera.androidclientproxy.lib.enums;

import com.google.gson.annotations.SerializedName;

public enum UserOrderStatusConverterTpye
{
    @SerializedName("0")
    ToStirng,

    @SerializedName("1")
    ToStatusBlockVisibility,

    @SerializedName("2")
    ToAddExchangeButtonVisibility,

    @SerializedName("3")
    ToColor,

    @SerializedName("4")
    ToFinishedVisibility
}

