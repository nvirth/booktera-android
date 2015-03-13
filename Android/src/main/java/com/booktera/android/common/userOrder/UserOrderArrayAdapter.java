package com.booktera.android.common.userOrder;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.booktera.android.R;
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

    public UserOrderArrayAdapter(FragmentActivity activity, List<UserOrderPLVM> data)
    {
        super(activity, layoutResourceId, data);

        this.activity = activity;
        this.data = data;
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

        if(onDataSetChangedListener != null)
            onDataSetChangedListener.run();
    }

    private Action onDataSetChangedListener;
    public void setOnDataSetChangedListener(Action onDataSetChangedListener)
    {
        this.onDataSetChangedListener = onDataSetChangedListener;
    }
}
