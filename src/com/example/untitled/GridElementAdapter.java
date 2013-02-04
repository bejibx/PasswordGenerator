package com.example.untitled;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

public class GridElementAdapter extends BaseAdapter {

    private final char[] mElements;
    private final LayoutInflater mInflater;

    public GridElementAdapter(Context context, String elements) {
        mElements = elements.toCharArray();
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mElements.length;
    }

    @Override
    public Object getItem(int i) {
        return mElements[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Button result;
        if (view == null) {
            result = (Button) mInflater.inflate(R.layout.grid_element, viewGroup, false);
        } else {
            result = (Button)view;
        }

        result.setText( getItem(i).toString() );
        return result;
    }
}
