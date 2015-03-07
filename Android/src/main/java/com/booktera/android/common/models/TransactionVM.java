package com.booktera.android.common.models;

import com.booktera.android.common.models.base.MapCache;
import com.booktera.androidclientproxy.lib.enums.TransactionType;
import com.booktera.androidclientproxy.lib.models.ProductModels.InCategoryPLVM;
import com.booktera.androidclientproxy.lib.models.UserOrderPLVM;

/**
 * Created by Norbert on 2015.02.09..
 */
public class TransactionVM extends MapCache<String, UserOrderPLVM[]>
{
    public static TransactionVM Instance = new TransactionVM();
    protected TransactionVM()
    {
    }

    // --

    public UserOrderPLVM[] getTransaction(TransactionType type)
    {
        return getValue(type.toKey());
    }
    public void setTransaction(UserOrderPLVM[] transactions, TransactionType type)
    {
        setValue(type.toKey(), transactions);
    }

    // --

    public UserOrderPLVM[] getCarts()
    {
        return getValue(TransactionType.Carts.toKey());
    }
    public void setCarts(UserOrderPLVM[] transactions)
    {
        setValue(TransactionType.Carts.toKey(), transactions);
    }

    public UserOrderPLVM[] getInCartsByOthers()
    {
        return getValue(TransactionType.InCartsByOthers.toKey());
    }
    public void setInCartsByOthers(UserOrderPLVM[] transactions)
    {
        setValue(TransactionType.InCartsByOthers.toKey(), transactions);
    }

    public UserOrderPLVM[] getInProgressBuys()
    {
        return getValue(TransactionType.InProgressBuys.toKey());
    }
    public void setInProgressBuys(UserOrderPLVM[] transactions)
    {
        setValue(TransactionType.InProgressBuys.toKey(), transactions);
    }

    public UserOrderPLVM[] getInProgressSells()
    {
        return getValue(TransactionType.InProgressSells.toKey());
    }
    public void setInProgressSells(UserOrderPLVM[] transactions)
    {
        setValue(TransactionType.InProgressSells.toKey(), transactions);
    }

    public UserOrderPLVM[] getEarlierBuys()
    {
        return getValue(TransactionType.EarlierBuys.toKey());
    }
    public void setEarlierBuys(UserOrderPLVM[] transactions)
    {
        setValue(TransactionType.EarlierBuys.toKey(), transactions);
    }

    public UserOrderPLVM[] getEarlierSells()
    {
        return getValue(TransactionType.EarlierSells.toKey());
    }
    public void setEarlierSells(UserOrderPLVM[] transactions)
    {
        setValue(TransactionType.EarlierSells.toKey(), transactions);
    }
}
