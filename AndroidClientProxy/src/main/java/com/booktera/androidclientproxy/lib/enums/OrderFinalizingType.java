package com.booktera.androidclientproxy.lib.enums;

import com.google.gson.annotations.SerializedName;


public enum OrderFinalizingType
{
    @SerializedName("0")
    WithoutExchange,

    @SerializedName("1")
    WithAccaptedExchange,

    @SerializedName("2")
    WithDeniedExchange
}

