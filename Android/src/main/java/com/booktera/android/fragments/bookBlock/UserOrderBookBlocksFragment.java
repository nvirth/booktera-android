package com.booktera.android.fragments.bookBlock;

import android.os.Bundle;
import com.booktera.android.common.Constants;
import com.booktera.android.common.models.MainVM;
import com.booktera.android.common.models.TransactionVM;
import com.booktera.android.common.utils.Utils;
import com.booktera.android.fragments.bookBlock.base.BookBlocksFragmentBase;
import com.booktera.androidclientproxy.lib.enums.BookBlockType;
import com.booktera.androidclientproxy.lib.enums.TransactionType;
import com.booktera.androidclientproxy.lib.models.ProductModels.BookBlockPLVM;
import com.booktera.androidclientproxy.lib.models.ProductModels.InBookBlockPVM;
import com.booktera.androidclientproxy.lib.models.UserOrderPLVM;
import com.booktera.androidclientproxy.lib.proxy.Services;

import java.util.List;

//todo remove if really not used
public class UserOrderBookBlocksFragment extends BookBlocksFragmentBase
{
    private static String tag = UserOrderBookBlocksFragment.class.toString();
    private TransactionType transactionType;
    private boolean isExchange;
    private int position;

    public static UserOrderBookBlocksFragment newInstance(TransactionType transactionType, boolean isExchange, UserOrderPLVM plvm)
    {
        UserOrderPLVM[] transactionsInType = TransactionVM.Instance.getTransaction(transactionType);
        int position = -1;
        for (int i = 0; i < transactionsInType.length; i++)
            if (transactionsInType[i] == plvm)
            {
                position = i;
                break;
            }

        if (position == -1)
            Utils.error("Cannot find position... transactionType: " + transactionType.name(), tag);

        return newInstance(transactionType, isExchange, position);
    }

    public static UserOrderBookBlocksFragment newInstance(TransactionType transactionType, boolean isExchange, int position)
    {
        Bundle args = new Bundle();
        args.putSerializable(Constants.PARAM_TRANSACTION_TYPE, transactionType);
        args.putBoolean(Constants.PARAM_IS_EXCHANGE, isExchange);
        args.putInt(Constants.PARAM_POSITION, position);

        UserOrderBookBlocksFragment fragment = new UserOrderBookBlocksFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        transactionType = (TransactionType) extractSerializableParam(Constants.PARAM_TRANSACTION_TYPE);
        isExchange = extractBooleanParam(Constants.PARAM_IS_EXCHANGE);
        position = extractIntParam(Constants.PARAM_POSITION);
    }

    @Override
    protected void loadData()
    {
        UserOrderPLVM[] transactionsInType = TransactionVM.Instance.getTransaction(transactionType);
        UserOrderPLVM transaction = transactionsInType[position];
        List<InBookBlockPVM> products = isExchange
            ? transaction.getExchangeProducts()
            : transaction.getProducts();

        BookBlockPLVM bookBlockPLVM = new BookBlockPLVM();
        bookBlockPLVM.setProducts(products);

        switch (transactionType)
        {
            case Carts:
                if (isExchange)
                    bookBlockPLVM.setBookBlockType(BookBlockType.ProductInExchangeCart);
                else
                    bookBlockPLVM.setBookBlockType(BookBlockType.ProductInCart);
                break;

            case InCartsByOthers:
            case InProgressBuys:
            case InProgressSells:
            case EarlierBuys:
            case EarlierSells:
            default:
                bookBlockPLVM.setBookBlockType(BookBlockType.ProductInOrder);
        }

        applyData(bookBlockPLVM);
    }
}

