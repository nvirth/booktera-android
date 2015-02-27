package com.booktera.android.common.bookBlock;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.booktera.android.BookteraApplication;
import com.booktera.android.R;
import com.booktera.android.activities.UsersProductsActivity;
import com.booktera.android.common.UserData;
import com.booktera.android.common.utils.Utils;
import com.booktera.android.fragments.bookBlock.UsersProductsFragment;
import com.booktera.androidclientproxy.lib.enums.BookBlockType;
import com.booktera.androidclientproxy.lib.enums.TransactionType;
import com.booktera.androidclientproxy.lib.enums.UserOrderStatus;
import com.booktera.androidclientproxy.lib.models.ProductModels.InBookBlockPVM;
import com.booktera.androidclientproxy.lib.models.UserOrderPLVM;

import java.util.List;

/**
 * Created by Norbert on 2015.02.10..
 */
public class BookBlockDataHolder
{
    public static abstract class Base
    {
        public List<InBookBlockPVM> products;
        public int actualPosition;
    }

    public static class Normal extends Base
    {
        public BookBlockType bookBlockType;
    }

    public static class UserOrder extends Base
    {
        public TransactionType transactionType;
        public UserOrderPLVM.UserOrderVM userOrder;
        public List<InBookBlockPVM> exchangeProducts;


        /**
         * TODO fix the description if it'll be actual
         * This is a helper for the global context menu in App.xaml. This indicates whenever
         * the product can be added to an Exchange cart. This is possible only, if one is
         * at the SearchPage, and went with the flag 'forExchange' true
         */
        public boolean isAddToExchangeCartPossible;
    }
}
