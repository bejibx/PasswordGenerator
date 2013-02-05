package com.example.untitled;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryAdapter {

    private final LayoutInflater mInflater;

    private ArrayList<Category> mCategories = new ArrayList<Category>();

    public CategoryAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addCategory(Category category) {
        mCategories.add(category);
    }

//    public void attachToView(ViewGroup parent) {
//        for (Category c : mCategories) {
//            View header = mInflater.inflate(R.layout.category_header, parent, false);
//            ((TextView) header.findViewById(R.id.Text)).setText(c.getName());
//            parent.addView(header);
//            GridView grid = (GridView) mInflater.inflate(R.layout.category_grid, parent, false);
//            grid.setAdapter(c.getAdapter());
//            parent.addView(grid);
//        }
//    }

}
