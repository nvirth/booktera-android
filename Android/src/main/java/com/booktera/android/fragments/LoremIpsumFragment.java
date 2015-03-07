package com.booktera.android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.booktera.android.R;

public class LoremIpsumFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        TextView loremIpsum = new TextView(getActivity());
        loremIpsum.setText(getString(R.string.loremIpsum));
        return loremIpsum;
    }
}

