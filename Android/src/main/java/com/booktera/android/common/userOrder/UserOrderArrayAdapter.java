package com.booktera.android.common.userOrder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.booktera.android.R;
import com.booktera.android.common.bookBlock.BookBlock;
import com.booktera.android.common.bookBlock.BookBlockDataHolder;
import com.booktera.androidclientproxy.lib.models.ProductModels.BookBlockPLVM;
import com.booktera.androidclientproxy.lib.models.ProductModels.InBookBlockPVM;
import com.booktera.androidclientproxy.lib.models.UserOrderPLVM;

/**
 * Created by Norbert on 2015.03.06..
 */
//todo UserOrderArrayAdapter
public class UserOrderArrayAdapter extends ArrayAdapter<UserOrderPLVM>
{
    public static final int layoutResourceId = R.layout.row_user_order;
    private Context context;

    private UserOrderPLVM[] data;

    public UserOrderArrayAdapter(Context context, UserOrderPLVM[] data)
    {
        super(context, layoutResourceId, data);

        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View userOrderView, ViewGroup parent)
    {
        UserOrderPLVM plvm = data[position];

        if (userOrderView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            userOrderView = inflater.inflate(layoutResourceId, parent, false);
            userOrderView.setTag(new UserOrder.ViewHolder(userOrderView));
        }

        UserOrder.ViewHolder vh = (UserOrder.ViewHolder) userOrderView.getTag();
        UserOrder userOrder = new UserOrder(plvm, vh, userOrderView, context);
        userOrder.fill();
        userOrder.setupContextMenu();

        return userOrderView;
    }
}
