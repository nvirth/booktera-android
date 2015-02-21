package com.booktera.androidclientproxy.lib.enums;

import com.google.gson.annotations.SerializedName;


public enum TransactionType
{
    @SerializedName("0")
    CartOwn,

    @SerializedName("1")
    CartOthers,

    @SerializedName("2")
    InProgressOrderOwn,

    @SerializedName("3")
    InProgressOrderOthers,

    @SerializedName("4")
    FinishedOrderOwn,

    @SerializedName("5")
    FinishedOrderOthers
}

