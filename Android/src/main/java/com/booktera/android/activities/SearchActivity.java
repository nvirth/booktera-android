package com.booktera.android.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import com.booktera.android.R;
import com.booktera.android.activities.base.ActionBarActivity;
import com.booktera.android.common.utils.Utils;
import com.booktera.android.fragments.bookBlock.SearchFragment;

public class SearchActivity extends ActionBarActivity
{
    protected ViewHolder vh;

    class ViewHolder
    {
        EditText searchTextBox;

        public ViewHolder(Activity activity)
        {
            searchTextBox = (EditText) activity.findViewById(R.id.tb_search);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        vh = new ViewHolder(this);
        vh.searchTextBox.setOnEditorActionListener(onEditorActionListener());
        vh.searchTextBox.setOnTouchListener(onTouchListener());

        search();
    }
    private TextView.OnEditorActionListener onEditorActionListener()
    {
        return (v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH)
            {
                search();
                Utils.hideKeyboard(this);
                return true;
            }
            return false;
        };
    }
    private View.OnTouchListener onTouchListener()
    {
        return (v, event) -> {
            final int DRAWABLE_LEFT = 0;
            final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            final int DRAWABLE_BOTTOM = 3;

            if (event == null || event.getAction() != MotionEvent.ACTION_UP)
                return false;

            int rightZoneWidth = vh.searchTextBox.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();
            int rightEdgeOfView = vh.searchTextBox.getRight();
            int rightZoneStart = rightEdgeOfView - rightZoneWidth;
            if (event.getRawX() >= rightZoneStart)
            {
                search();
                Utils.hideKeyboard(this);

                return true;
            }
            return false;
        };
    }
    private void search()
    {
        String searchInput = vh.searchTextBox.getText().toString();
        if (searchInput == null || searchInput.length() < 3)
            return;

        // Here happens the search, by creating a new fragment
        runInFragmentTransaction(
            transaction -> transaction.replace(
                R.id.fragmentContainer,
                SearchFragment.newInstance(searchInput)
            ));
    }
}
