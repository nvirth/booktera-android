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
    private Context context;

    private BookBlockDataHolder.Base data;

    public BookBlockArrayAdapter(Context context, BookBlockPLVM data)
    {
        super(context, layoutResourceId, data.getProducts());

        this.context = context;

        BookBlockDataHolder.Normal regularBBdata = new BookBlockDataHolder.Normal();
        regularBBdata.bookBlockType = data.getBookBlockType();
        regularBBdata.products = data.getProducts();

        this.data = regularBBdata;
    }
    public BookBlockArrayAdapter(Context context, UserOrderPLVM data)
    {
        super(context, layoutResourceId, data.getProducts());

        this.context = context;

        BookBlockDataHolder.UserOrder userOrderData = new BookBlockDataHolder.UserOrder();
        userOrderData.products = data.getProducts();
        userOrderData.exchangeProducts = data.getExchangeProducts();
        userOrderData.transactionType = data.getTransactionType();
        userOrderData.userOrder = data.getUserOrder();

        this.data = userOrderData;
    }

    @Override
    public View getView(int position, View bookBlockView, ViewGroup parent)
    {
        data.actualPosition = position;
        InBookBlockPVM vm = data.products.get(position);

        if (bookBlockView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            bookBlockView = inflater.inflate(layoutResourceId, parent, false);
            bookBlockView.setTag(new BookBlock.ViewHolder(bookBlockView));
        }

        BookBlock.ViewHolder vh = (BookBlock.ViewHolder) bookBlockView.getTag();
        BookBlock bookBlock = new BookBlock(vm, vh, bookBlockView, context, data);
        bookBlock.fill();
        bookBlock.setupContextMenu();

        return bookBlockView;
    }
}
