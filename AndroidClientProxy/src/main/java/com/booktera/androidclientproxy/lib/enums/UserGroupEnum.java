package com.booktera.androidclientproxy.lib.enums;

import com.google.gson.annotations.SerializedName;


public enum UserGroupEnum
{
    @SerializedName("0")
    Nincs,

    @SerializedName("1")
    Leech,

    @SerializedName("2")
    Seed,

    @SerializedName("3")
    SeedPlus,

    @SerializedName("4")
    Author,

    @SerializedName("5")
    Publisher
}

