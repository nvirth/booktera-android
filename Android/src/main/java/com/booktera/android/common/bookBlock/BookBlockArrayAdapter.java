package com.booktera.android.common.bookBlock;

import android.app.Activity;
import android.view.*;
import android.widget.ArrayAdapter;
import com.booktera.android.R;
import com.booktera.androidclientproxy.lib.models.ProductModels.BookBlockPLVM;
import com.booktera.androidclientproxy.lib.models.ProductModels.InBookBlockPVM;

/**
 * For regular BookBlocks (which is not in transaction)
 */
public class BookBlockArrayAdapter extends ArrayAdapter<InBookBlockPVM>
{
    public static final int layoutResourceId = R.layout.row_book_block;
    private Activity activity;
    private BookBlockPLVM data;
    private int userOrderId_forExchange;

    public BookBlockArrayAdapter(Activity activity, BookBlockPLVM data)
    {
        this(activity, data, -1);
    }
    public BookBlockArrayAdapter(Activity activity, BookBlockPLVM data, int userOrderId)
    {
        super(activity, layoutResourceId, data.getProducts());

        this.activity = activity;
        this.data = data;
        this.userOrderId_forExchange = userOrderId;
    }

    @Override
    public View getView(int position, View bookBlockView, ViewGroup parent)
    {
        InBookBlockPVM vm = data.getProducts().get(position);

        if (bookBlockView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(activity);
            bookBlockView = inflater.inflate(layoutResourceId, parent, false);
            bookBlockView.setTag(new BookBlock.ViewHolder(bookBlockView));
        }

        BookBlock.ViewHolder vh = (BookBlock.ViewHolder) bookBlockView.getTag();
        BookBlock bookBlock = new BookBlock(new BookBlock.CtorArgs(
            vm, vh, bookBlockView, activity, /*isExchangeProduct*/ false,
            /*userOrderVm*/null, userOrderId_forExchange)
        );
        bookBlock.fill();
        bookBlock.setupContextMenu();

        return bookBlockView;
    }
}
