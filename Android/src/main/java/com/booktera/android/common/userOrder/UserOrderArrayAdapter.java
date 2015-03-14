package com.booktera.android.common.userOrder;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.booktera.android.R;
import com.booktera.android.common.models.TransactionVM;
import com.booktera.androidclientproxy.lib.enums.TransactionType;
import com.booktera.androidclientproxy.lib.models.UserOrderPLVM;
import com.booktera.androidclientproxy.lib.utils.Action;

import java.util.List;

/**
 * Created by Norbert on 2015.03.06..
 */
public class UserOrderArrayAdapter extends ArrayAdapter<UserOrderPLVM>
{
    public static final int layoutResourceId = R.layout.row_user_order;
    private FragmentActivity activity;

    private List<UserOrderPLVM> data;

    public UserOrderArrayAdapter(FragmentActivity activity, List<UserOrderPLVM> data, TransactionType transactionType)
    {
        super(activity, layoutResourceId, data);

        this.activity = activity;
        this.data = data;

        // -- Interactivity

        // OrderSent
        if (transactionType == TransactionType.InProgressBuys)
            TransactionVM.Instance.setOrderSentHandler_add(this::add);
        else if (transactionType == TransactionType.Carts)
            TransactionVM.Instance.setOrderSentHandler_remove(this::remove);

        // OrderClosed
        if (transactionType == TransactionType.InProgressBuys
            || transactionType == TransactionType.InProgressSells)
            TransactionVM.Instance.setOrderClosedHandler_remove(this::remove);
        else if (transactionType == TransactionType.EarlierBuys
            || transactionType == TransactionType.EarlierSells)
            TransactionVM.Instance.setOrderClosedHandler_add(this::add);
    }


    @Override
    public View getView(int position, View userOrderView, ViewGroup parent)
    {
        UserOrderPLVM plvm = data.get(position);

        if (userOrderView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(activity);
            userOrderView = inflater.inflate(layoutResourceId, parent, false);
            userOrderView.setTag(new UserOrder.ViewHolder(userOrderView));
        }

        UserOrder.ViewHolder vh = (UserOrder.ViewHolder) userOrderView.getTag();
        UserOrder userOrder = new UserOrder(plvm, vh, userOrderView, activity, this);
        userOrder.fill();
        userOrder.setupContextMenu();

        return userOrderView;
    }

    @Override
    public void notifyDataSetChanged()
    {
        super.notifyDataSetChanged();

        if (onDataSetChangedListener != null)
            onDataSetChangedListener.run();
    }

    private Action onDataSetChangedListener;
    public void setOnDataSetChangedListener(Action onDataSetChangedListener)
    {
        this.onDataSetChangedListener = onDataSetChangedListener;
    }
}
