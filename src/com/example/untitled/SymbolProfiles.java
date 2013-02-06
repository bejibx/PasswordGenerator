package com.example.untitled;

import android.content.Context;

import java.io.File;

public class SymbolProfiles {

    private static final String PROFILES_SUBDIR_NAME = "profiles";

    private final Context mContext;
    private final File mProfilesDir;
    private boolean mIsActive = false;

    public SymbolProfiles(Context context) {
        mContext = context;

        mProfilesDir = new File(mContext.getFilesDir().getPath() + File.pathSeparator + PROFILES_SUBDIR_NAME);
        if ( !mProfilesDir.exists() ) {
            boolean succcess = mProfilesDir.mkdir();
            if (succcess) {
                mContext.getResources().getXml(R.xml.categories);
            }
        }
    }

}
