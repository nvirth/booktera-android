package com.booktera.android.fragments.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.booktera.android.R;
import com.booktera.android.common.bookBlock.BookBlockArrayAdapter;
import com.booktera.android.common.utils.Utils;
import com.booktera.androidclientproxy.lib.models.ProductModels.BookBlockPLVM;

public abstract class ListViewFragmentBase extends BaseFragment
{
    protected ViewHolder vh;

    protected class ViewHolder
    {
        public ListView listView;
        public TextView noResultTextView;

        public ViewHolder()
        {
            View view = getView();
            if (view == null)
                Utils.error("The getView() call ended up with a null", tag);

            construct(view);
        }
        public ViewHolder(View view)
        {
            construct(view);
        }
        private void construct(View view)
        {
            listView = (ListView) view.findViewById(R.id.listviewListView);
            noResultTextView = (TextView) view.findViewById(R.id.listviewNoResults);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.listview, container, false);
        vh = new ViewHolder(view);
        loadData();

        return view;
    }

    protected abstract void loadData();
}

