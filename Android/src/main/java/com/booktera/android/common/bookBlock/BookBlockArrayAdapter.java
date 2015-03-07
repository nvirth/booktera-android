package com.booktera.android.common.bookBlock;

import android.content.Context;
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
    private Context context;
    private BookBlockPLVM data;

    public BookBlockArrayAdapter(Context context, BookBlockPLVM data)
    {
        super(context, layoutResourceId, data.getProducts());

        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View bookBlockView, ViewGroup parent)
    {
        InBookBlockPVM vm = data.getProducts().get(position);

        if (bookBlockView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            bookBlockView = inflater.inflate(layoutResourceId, parent, false);
            bookBlockView.setTag(new BookBlock.ViewHolder(bookBlockView));
        }

        BookBlock.ViewHolder vh = (BookBlock.ViewHolder) bookBlockView.getTag();
        BookBlock bookBlock = new BookBlock(new BookBlock.CtorArgs(vm, vh, bookBlockView, context));
        bookBlock.fill();
        bookBlock.setupContextMenu();

        return bookBlockView;
    }
}
