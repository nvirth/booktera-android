package com.booktera.androidclientproxy.lib.enums;

import com.google.gson.annotations.SerializedName;

public enum HttpPostVerb
{
    @SerializedName("0")
    NON_POST__GET,

    @SerializedName("1")
    POST,

    @SerializedName("2")
    PUT,

    @SerializedName("3")
    DELETE
}

