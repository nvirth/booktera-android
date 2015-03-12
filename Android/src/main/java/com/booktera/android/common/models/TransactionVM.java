package com.booktera.android.common.models;

import com.booktera.android.common.models.base.MapCache;
import com.booktera.androidclientproxy.lib.enums.TransactionType;
import com.booktera.androidclientproxy.lib.models.ProductModels.InCategoryPLVM;
import com.booktera.androidclientproxy.lib.models.UserOrderPLVM;

import java.util.List;

/**
 * Created by Norbert on 2015.02.09..
 */
public class TransactionVM extends MapCache<String, List<UserOrderPLVM>>
{
    public static TransactionVM Instance = new TransactionVM();
    protected TransactionVM()
    {
    }

    // --

    public List<UserOrderPLVM> getTransaction(TransactionType type)
    {
        return getValue(type.toKey());
    }
    public void setTransaction(List<UserOrderPLVM> transactions, TransactionType type)
    {
        setValue(type.toKey(), transactions);
    }

    // --

    public List<UserOrderPLVM> getCarts()
    {
        return getValue(TransactionType.Carts.toKey());
    }
    public void setCarts(List<UserOrderPLVM> transactions)
    {
        setValue(TransactionType.Carts.toKey(), transactions);
    }

    public List<UserOrderPLVM> getInCartsByOthers()
    {
        return getValue(TransactionType.InCartsByOthers.toKey());
    }
    public void setInCartsByOthers(List<UserOrderPLVM> transactions)
    {
        setValue(TransactionType.InCartsByOthers.toKey(), transactions);
    }

    public List<UserOrderPLVM> getInProgressBuys()
    {
        return getValue(TransactionType.InProgressBuys.toKey());
    }
    public void setInProgressBuys(List<UserOrderPLVM> transactions)
    {
        setValue(TransactionType.InProgressBuys.toKey(), transactions);
    }

    public List<UserOrderPLVM> getInProgressSells()
    {
        return getValue(TransactionType.InProgressSells.toKey());
    }
    public void setInProgressSells(List<UserOrderPLVM> transactions)
    {
        setValue(TransactionType.InProgressSells.toKey(), transactions);
    }

    public List<UserOrderPLVM> getEarlierBuys()
    {
        return getValue(TransactionType.EarlierBuys.toKey());
    }
    public void setEarlierBuys(List<UserOrderPLVM> transactions)
    {
        setValue(TransactionType.EarlierBuys.toKey(), transactions);
    }

    public List<UserOrderPLVM> getEarlierSells()
    {
        return getValue(TransactionType.EarlierSells.toKey());
    }
    public void setEarlierSells(List<UserOrderPLVM> transactions)
    {
        setValue(TransactionType.EarlierSells.toKey(), transactions);
    }
}
