package com.example.untitled;

import android.app.Application;

import java.io.File;

public class _Application extends Application {

    private CategoryAdapter mCategoryAdapter;

    @Override
    public void onCreate() {
        super.onCreate();

        mCategoryAdapter = new CategoryAdapter(this);

        mCategoryAdapter.initializeFromXML(getResources().getXml(R.xml.categories));
    }

    public CategoryAdapter getAdapter() {
        return mCategoryAdapter;
    }
}
