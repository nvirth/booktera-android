package com.booktera.android.fragments.userOrder;

import android.os.Bundle;
import com.booktera.android.common.Constants;
import com.booktera.android.common.UserData;
import com.booktera.android.common.models.TransactionVM;
import com.booktera.android.common.utils.Utils;
import com.booktera.android.fragments.userOrder.base.UserOrderFragmentBase;
import com.booktera.androidclientproxy.lib.enums.TransactionType;
import com.booktera.androidclientproxy.lib.models.UserOrderPLVM;
import com.booktera.androidclientproxy.lib.proxy.Services;
import com.booktera.androidclientproxy.lib.utils.Action_1;
import com.booktera.androidclientproxy.lib.utils.Action_4;
import org.apache.http.HttpResponse;

import java.util.List;

public class UserOrderFragment extends UserOrderFragmentBase
{
    public static UserOrderFragment newInstance(TransactionType transactionType)
    {
        Bundle args = new Bundle();
        args.putSerializable(Constants.PARAM_TRANSACTION_TYPE, transactionType);

        // Always refresh the caches when instantiating from Activity/ViewPagerAdapter.
        // This way, they will be refreshed:
        // * When the user navigates to a Transaction page (from ActionBar) (+)
        // They won't be refreshed:
        // * On orientation change (=Activity recreate) (+)
        // ** They should be, but they won't, because the newly instantiated ViewPagerAdapter
        //     somehow reuses the existing fragments :)
        // * On page change (ViewPager) (+)
        args.putBoolean(Constants.PARAM_REFRESH_CACHE, true);

        UserOrderFragment fragment = new UserOrderFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        boolean isCacheValid = TransactionVM.Instance.isTransactionCacheValid(transactionType);
        if(!isCacheValid)
            reloadData();
    }

    @Override
    protected void loadData()
    {
        boolean refreshCache = getArguments().getBoolean(Constants.PARAM_REFRESH_CACHE, false);

        if (TransactionVM.Instance.getTransaction(transactionType) == null)
        {
            reloadData();
        }
        else
        {
            applyData(TransactionVM.Instance.getTransaction(transactionType));
            if (refreshCache)
                reloadData();
        }
    }
    private void reloadData()
    {
        SelectActionResult sar = selectAction();
        sar.serviceCall.run(sar.customerId, sar.vendorId,
            userOrderPLVMs -> /*success*/{
                TransactionVM.Instance.setTransaction(userOrderPLVMs, transactionType);
                applyData(userOrderPLVMs);

                // Cache refreshed
                getArguments().putBoolean(Constants.PARAM_REFRESH_CACHE, false);
            }, null);
    }

    private static class SelectActionResult
    {
        public Action_4<Integer, Integer, Action_1<List<UserOrderPLVM>>, Action_1<HttpResponse>> serviceCall;
        public Integer vendorId;
        public Integer customerId;
    }

    private SelectActionResult selectAction()
    {
        SelectActionResult res = new SelectActionResult();

        switch (transactionType)
        {
            case Carts:
                res.serviceCall = Services.TransactionManager::getUsersCartsVM;
                res.vendorId = null;
                res.customerId = UserData.Instace.getUserId();
                break;
            case InCartsByOthers:
                res.serviceCall = Services.TransactionManager::getUsersCartsVM;
                res.customerId = null;
                res.vendorId = UserData.Instace.getUserId();
                break;
            case InProgressBuys:
                res.serviceCall = Services.TransactionManager::getUsersInProgressOrdersVM;
                res.vendorId = null;
                res.customerId = UserData.Instace.getUserId();
                break;
            case InProgressSells:
                res.serviceCall = Services.TransactionManager::getUsersInProgressOrdersVM;
                res.customerId = null;
                res.vendorId = UserData.Instace.getUserId();
                break;
            case EarlierBuys:
                res.serviceCall = Services.TransactionManager::getUsersFinishedTransactionsVM;
                res.vendorId = null;
                res.customerId = UserData.Instace.getUserId();
                break;
            case EarlierSells:
                res.serviceCall = Services.TransactionManager::getUsersFinishedTransactionsVM;
                res.customerId = null;
                res.vendorId = UserData.Instace.getUserId();
                break;
            default:
                Utils.error("The method 'selectAction' is not prepared for transaction type: " + transactionType, tag);
        }

        return res;
    }
}

