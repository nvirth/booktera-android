package com.booktera.android.fragments.bookBlock.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.booktera.android.R;
import com.booktera.android.common.bookBlock.BookBlockArrayAdapter;
import com.booktera.androidclientproxy.lib.models.ProductModels.BookBlockPLVM;

public abstract class BookBlocksFragment extends Fragment
{
    private static final String tag = BookBlocksFragment.class.toString();
    ViewHolder vh;

    class ViewHolder
    {
        ListView bookBlocksLv;

        public ViewHolder()
        {
            View view = getView();
            if (view == null)
            {
                String msg = "The getView() call ended up with a null";
                RuntimeException e = new RuntimeException(msg);
                Log.e(tag, msg, e);
                throw e;
            }

            construct(view);
        }
        public ViewHolder(View view)
        {
            construct(view);
        }
        private void construct(View view)
        {
            bookBlocksLv = (ListView) view.findViewById(R.id.bookBlocksLv);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_book_blocks, container, false);
        vh = new ViewHolder(view);
        loadData(view);

        return view;
    }

    /**
     * While implementing, you have to call {@link #applyData(android.view.View, com.booktera.androidclientproxy.lib.models.ProductModels.BookBlockPLVM)}
     * with the (asynchronously)downloaded/cached data
     */
    protected abstract void loadData(View view);

    protected void applyData(View view, BookBlockPLVM data)
    {
        getActivity().runOnUiThread(() ->
        {
            BookBlockArrayAdapter bookBlockArrayAdapter = new BookBlockArrayAdapter(
                getActivity().getApplicationContext(),
                data
            );

            vh.bookBlocksLv.setAdapter(bookBlockArrayAdapter);
        });
    }
}

