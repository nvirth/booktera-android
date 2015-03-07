package com.booktera.androidclientproxy.lib.enums;

import com.google.gson.annotations.SerializedName;

public enum TransactionType
{
    @SerializedName("0")
    Carts,

    @SerializedName("1")
    InCartsByOthers,

    @SerializedName("2")
    InProgressBuys,

    @SerializedName("3")
    InProgressSells,

    @SerializedName("4")
    EarlierBuys,

    @SerializedName("5")
    EarlierSells;

    public String toKey()
    {
        switch (this)
        {
            case Carts:
                return "KEY_CARTS";
            case InCartsByOthers:
                return "KEY_IN_CARTS_BY_OTHERS";
            case InProgressBuys:
                return "KEY_IN_PROGRESS_BUYS";
            case InProgressSells:
                return "KEY_IN_PROGRESS_SELLS";
            case EarlierBuys:
                return "KEY_EARLIER_BUYS";
            case EarlierSells:
                return "KEY_EARLIER_SELLS";
            default:
                return super.toString();
        }
    }
}

