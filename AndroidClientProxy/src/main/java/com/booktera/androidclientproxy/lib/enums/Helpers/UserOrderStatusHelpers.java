package com.booktera.androidclientproxy.lib.enums.Helpers;

import com.booktera.androidclientproxy.lib.enums.UserOrderStatus;

public class UserOrderStatusHelpers
{
    public static String toDescriptionString(UserOrderStatus userOrderStatus){
        switch(userOrderStatus)
        {
            // Normal
            case Cart: 
                return "Kosárban";
            case BuyedWaiting: 
                return "Válaszra vár";
            case BuyedExchangeOffered: 
                return "Csere ajánlva";
            case FinalizedExchange: 
                return "Véglegesített tranzakció cserével";
            case FinalizedCash: 
                return "Véglegesített tranzakció fizetéssel";
            case Finished: 
                return "Befejezett tranzakció";

            // Not exist in DB
            case CartDeleting: 
                return "Kosár törlés alatt";

            default: 
                return userOrderStatus.toString();
        
        }
    }
}