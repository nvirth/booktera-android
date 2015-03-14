package com.booktera.android.common.models;

import com.booktera.android.common.models.base.MapCache;
import com.booktera.android.common.userOrder.UserOrder;
import com.booktera.android.common.utils.Utils;
import com.booktera.androidclientproxy.lib.enums.TransactionType;
import com.booktera.androidclientproxy.lib.enums.UserOrderStatus;
import com.booktera.androidclientproxy.lib.models.UserOrderPLVM;
import com.booktera.androidclientproxy.lib.utils.Action;
import com.booktera.androidclientproxy.lib.utils.Action_1;

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

    //region Transactions (general)
    public List<UserOrderPLVM> getTransaction(TransactionType type)
    {
        return getValue(type.toKey());
    }
    public void setTransaction(List<UserOrderPLVM> transactions, TransactionType type)
    {
        setValue(type.toKey(), transactions);
    }
    public boolean isTransactionCacheValid(TransactionType type)
    {
        return isValid(type.toKey());
    }
    public void invalidateTransactionCache(TransactionType type)
    {
        invalidate(type.toKey());
    }
    //endregion

    //region Transactions by type
    public List<UserOrderPLVM> getCarts()
    {
        return getValue(TransactionType.Carts.toKey());
    }
    public void setCarts(List<UserOrderPLVM> transactions)
    {
        setValue(TransactionType.Carts.toKey(), transactions);
    }
    public boolean isCartsCacheValid()
    {
        return isValid(TransactionType.Carts.toKey());
    }
    public void invalidateCartsCache()
    {
        invalidate(TransactionType.Carts.toKey());
    }

    public List<UserOrderPLVM> getInCartsByOthers()
    {
        return getValue(TransactionType.InCartsByOthers.toKey());
    }
    public void setInCartsByOthers(List<UserOrderPLVM> transactions)
    {
        setValue(TransactionType.InCartsByOthers.toKey(), transactions);
    }
    public boolean isInCartsByOthersCacheValid()
    {
        return isValid(TransactionType.InCartsByOthers.toKey());
    }
    public void invalidateInCartsByOthersCache()
    {
        invalidate(TransactionType.InCartsByOthers.toKey());
    }

    public List<UserOrderPLVM> getInProgressBuys()
    {
        return getValue(TransactionType.InProgressBuys.toKey());
    }
    public void setInProgressBuys(List<UserOrderPLVM> transactions)
    {
        setValue(TransactionType.InProgressBuys.toKey(), transactions);
    }
    public boolean isInProgressBuysCacheValid()
    {
        return isValid(TransactionType.InProgressBuys.toKey());
    }
    public void invalidateInProgressBuysCache()
    {
        invalidate(TransactionType.InProgressBuys.toKey());
    }

    public List<UserOrderPLVM> getInProgressSells()
    {
        return getValue(TransactionType.InProgressSells.toKey());
    }
    public void setInProgressSells(List<UserOrderPLVM> transactions)
    {
        setValue(TransactionType.InProgressSells.toKey(), transactions);
    }
    public boolean isInProgressSellsCacheValid()
    {
        return isValid(TransactionType.InProgressSells.toKey());
    }
    public void invalidateInProgressSellsCache()
    {
        invalidate(TransactionType.InProgressSells.toKey());
    }

    public List<UserOrderPLVM> getEarlierBuys()
    {
        return getValue(TransactionType.EarlierBuys.toKey());
    }
    public void setEarlierBuys(List<UserOrderPLVM> transactions)
    {
        setValue(TransactionType.EarlierBuys.toKey(), transactions);
    }
    public boolean isEarlierBuysCacheValid()
    {
        return isValid(TransactionType.EarlierBuys.toKey());
    }
    public void invalidateEarlierBuysCache()
    {
        invalidate(TransactionType.EarlierBuys.toKey());
    }

    public List<UserOrderPLVM> getEarlierSells()
    {
        return getValue(TransactionType.EarlierSells.toKey());
    }
    public void setEarlierSells(List<UserOrderPLVM> transactions)
    {
        setValue(TransactionType.EarlierSells.toKey(), transactions);
    }
    public boolean isEarlierSellsCacheValid()
    {
        return isValid(TransactionType.EarlierSells.toKey());
    }
    public void invalidateEarlierSellsCache()
    {
        invalidate(TransactionType.EarlierSells.toKey());
    }
    //endregion

    //region Events
    //region orderSent
    private Action_1<UserOrderPLVM> orderSentHandler_add;
    private Action_1<UserOrderPLVM> orderSentHandler_remove;
    public void setOrderSentHandler_add(Action_1<UserOrderPLVM> orderSentHandler_add)
    {
        this.orderSentHandler_add = orderSentHandler_add;
    }
    public void setOrderSentHandler_remove(Action_1<UserOrderPLVM> orderSentHandler_remove)
    {
        this.orderSentHandler_remove = orderSentHandler_remove;
    }
    public void onOrderSent(UserOrderPLVM plvm)
    {
        // Put it (virtually) from Carts to InProgressBuys
        plvm.setTransactionType(TransactionType.InProgressBuys);
        plvm.getUserOrder().setStatus(UserOrderStatus.BuyedWaiting);

        if (orderSentHandler_remove == null)
            Utils.error("orderSentHandler_remove should be set", tag);
        else
            orderSentHandler_remove.run(plvm);

        // It's possible (in current circumstances only in theory though, 2015.03.13), that the
        // add-handler is null. In this case, there is no ArrayAdapter instantiated yet for
        // InProgressBuy transactions. So in this case, we have to add the transaction to them
        // manually
        // NOTE. Consider this flow:
        // User navigates to Buys, so
        // 1. orderSentHandler_add get set
        // 2. orderSentHandler_remove get set
        // User navigates off, then navigates to Buys again, but not backwards!
        // 3. orderSentHandler_add get set
        // 4. orderSentHandler_remove get set
        //
        // If we are on step 3, and not on step 1, this won't work!
        if (orderSentHandler_add == null)
            getInProgressBuys().add(plvm);
        else
            orderSentHandler_add.run(plvm);

    }
    //endregion

    //region orderClosed
    private Action_1<UserOrderPLVM> orderClosedHandler_add;
    private Action_1<UserOrderPLVM> orderClosedHandler_remove;
    public void setOrderClosedHandler_add(Action_1<UserOrderPLVM> orderClosedHandler_add)
    {
        this.orderClosedHandler_add = orderClosedHandler_add;
    }
    public void setOrderClosedHandler_remove(Action_1<UserOrderPLVM> orderClosedHandler_remove)
    {
        this.orderClosedHandler_remove = orderClosedHandler_remove;
    }
    public void onOrderClosed(UserOrderPLVM plvm, UserOrder uo, boolean wasSuccessful)
    {
        boolean needToMoveToAnotherTab = false;
        Action ifAddHandlerNotSet = () -> {
        };
        switch (plvm.getTransactionType())
        {
            // Need to move to another tab
            //
            case InProgressBuys:
                plvm.setTransactionType(TransactionType.EarlierBuys);
                plvm.getUserOrder().setStatus(UserOrderStatus.Finished);
                plvm.getUserOrder().setCustomerFeedbackedSuccessful(wasSuccessful);

                ifAddHandlerNotSet = () -> getEarlierBuys().add(plvm);

                needToMoveToAnotherTab = true;
                break;
            case InProgressSells:
                plvm.setTransactionType(TransactionType.EarlierSells);
                plvm.getUserOrder().setStatus(UserOrderStatus.Finished);
                plvm.getUserOrder().setVendorFeedbackedSuccessful(wasSuccessful);

                ifAddHandlerNotSet = () -> getEarlierSells().add(plvm);

                needToMoveToAnotherTab = true;
                break;

            // No need to move to another tab
            //
            case EarlierSells: // Vendor's side
                plvm.getUserOrder().setVendorFeedbackedSuccessful(true);
                uo.fill();
                needToMoveToAnotherTab = false;
                break;

            case EarlierBuys: // Customer's side
                plvm.getUserOrder().setCustomerFeedbackedSuccessful(true);
                uo.fill();
                needToMoveToAnotherTab = false;
                break;
        }
        if (needToMoveToAnotherTab)
        {
            if (orderClosedHandler_remove == null)
                Utils.error("orderClosedHandler_remove should be set", tag);
            else
                orderClosedHandler_remove.run(plvm);

            // For possible issues, see onOrderSent(...)
            if (orderClosedHandler_add == null)
                ifAddHandlerNotSet.run();
            else
                orderClosedHandler_add.run(plvm);
        }
    }
    //endregion
    //endregion
}
