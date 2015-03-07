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

public class UserOrderFragment extends UserOrderFragmentBase
{
    private TransactionType transactionType;

    public static UserOrderFragment newInstance(TransactionType transactionType)
    {
        Bundle args = new Bundle();
        args.putSerializable(Constants.PARAM_TRANSACTION_TYPE, transactionType);

        UserOrderFragment fragment = new UserOrderFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        transactionType = (TransactionType) extractSerializableParam(Constants.PARAM_TRANSACTION_TYPE);
    }

    @Override
    protected void loadData()
    {
        if (TransactionVM.Instance.getTransaction(transactionType) == null)
        {
            SelectActionResult sar = selectAction();
            sar.serviceCall.run(sar.customerId, sar.vendorId, userOrderPLVMs -> {
                TransactionVM.Instance.setTransaction(userOrderPLVMs, transactionType);
                applyData(userOrderPLVMs);
            }, null);
        }
        else
        {
            applyData(TransactionVM.Instance.getTransaction(transactionType));
        }
    }

    private static class SelectActionResult
    {
        public Action_4<Integer, Integer, Action_1<UserOrderPLVM[]>, Action_1<HttpResponse>> serviceCall;
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

