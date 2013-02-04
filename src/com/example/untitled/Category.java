package com.example.untitled;

import android.content.Context;
import android.widget.BaseAdapter;

public class Category {

    private final String mName;
    private final GridElementAdapter mGrid;

    public Category(String name, GridElementAdapter gridElementAdapter) {
        mName = name;
        mGrid = gridElementAdapter;
    }

    public String getName() {
        return mName;
    }

    public GridElementAdapter getAdapter() {
        return mGrid;
    }
}
