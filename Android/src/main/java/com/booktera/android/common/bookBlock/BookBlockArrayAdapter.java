package com.booktera.android.common.bookBlock;

import android.content.Context;
import android.view.*;
import android.widget.ArrayAdapter;
import com.booktera.android.R;
import com.booktera.androidclientproxy.lib.enums.BookBlockType;
import com.booktera.androidclientproxy.lib.models.ProductModels.BookBlockPLVM;
import com.booktera.androidclientproxy.lib.models.ProductModels.InBookBlockPVM;
import com.booktera.androidclientproxy.lib.models.UserOrderPLVM;

import java.util.List;

public class BookBlockArrayAdapter extends ArrayAdapter<InBookBlockPVM>
{
    public static final int layoutResourceId = R.layout.row_book_block;
    Context context;

    BookBlock.DataHolder.Base data;

    public BookBlockArrayAdapter(Context context, BookBlockPLVM data)
    {
        super(context, layoutResourceId, data.getProducts());

        this.context = context;

        BookBlock.DataHolder.Normal regularBBdata = new BookBlock.DataHolder.Normal();
        regularBBdata.bookBlockType = data.getBookBlockType();
        regularBBdata.products = data.getProducts();

        this.data = regularBBdata;
    }
    public BookBlockArrayAdapter(Context context, UserOrderPLVM data)
    {
        super(context, layoutResourceId, data.getProducts());

        this.context = context;

        BookBlock.DataHolder.UserOrder userOrderData = new BookBlock.DataHolder.UserOrder();
        userOrderData.products = data.getProducts();
        userOrderData.exchangeProducts = data.getExchangeProducts();
        userOrderData.transactionType = data.getTransactionType();
        userOrderData.userOrder = data.getUserOrder();

        this.data = userOrderData;
    }

    @Override
    public View getView(int position, View bookBlockView, ViewGroup parent)
    {
        boolean wasFirst = false;
        if (bookBlockView == null)
        {
            wasFirst = true;

            LayoutInflater inflater = LayoutInflater.from(context);
            bookBlockView = inflater.inflate(layoutResourceId, parent, false);
            bookBlockView.setTag(new BookBlock.ViewHolder(bookBlockView));
        }

        data.actualPosition = position;
        InBookBlockPVM vm = data.products.get(position);
        if (vm != null)
        {
            BookBlock.ViewHolder vh = (BookBlock.ViewHolder) bookBlockView.getTag();
            BookBlock.fill(vh, vm);

            if (wasFirst)
                BookBlock.setupContextMenu(bookBlockView, context, vh, vm, data);
        }

        return bookBlockView;
    }
}
