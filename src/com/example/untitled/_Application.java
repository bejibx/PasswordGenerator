package com.example.untitled;

import android.app.Application;

public class _Application extends Application {

    private SymbolProfiles mSymbolProfiles;

    @Override
    public void onCreate() {
        super.onCreate();

        mSymbolProfiles = new SymbolProfiles(this);
    }

    public SymbolProfiles getProfiller() {
        return mSymbolProfiles;
    }
}
