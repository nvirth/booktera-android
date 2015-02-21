package com.booktera.androidclientproxy.lib.enums;

import com.google.gson.annotations.SerializedName;

public enum BookBlockType
{
    // -- BookBlockPLVM

    @SerializedName("0")
    Product,

    @SerializedName("1")
    ProductGroup,

    // -- UserOrderPLVM

    @SerializedName("2")
    ProductInCart,

    @SerializedName("3")
    ProductInOrder,

    @SerializedName("4")
    ProductInExchangeCart,

    @SerializedName("5")
    ProductForExchange
}

