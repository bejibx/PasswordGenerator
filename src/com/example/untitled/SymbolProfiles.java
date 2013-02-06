package com.example.untitled;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.*;

public class SymbolProfiles {

    private static final String PROFILES_SUBDIR_NAME = "profiles";
    public static final String DEFAULT_PROFILE = "default.xml";

    private final Context mContext;
    private final File mProfilesDir;
    private boolean mIsActive = false;

    private void uploadDefaultProfile() {
        AssetManager assetManager = mContext.getResources().getAssets();

        File output = new File(mProfilesDir.getPath(), DEFAULT_PROFILE);
        InputStream is = null;
        try {
            is = assetManager.open(DEFAULT_PROFILE);
            OutputStream os = new FileOutputStream(output, true);
            final int buffer_size = 1024 * 1024;
            byte[] bytes = new byte[buffer_size];
            int bytesRead;

            while ((bytesRead = is.read(bytes, 0, buffer_size)) != -1) {
                os.write(bytes, 0, bytesRead);
            }

//                for (;;)
//                {
//                    int count = is.read(bytes, 0, buffer_size);
//                    if (count == -1)
//                        break;
//                    os.write(bytes, 0, count);
//                }
            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SymbolProfiles(Context context) {
        mContext = context;

        mProfilesDir = new File(mContext.getFilesDir().getPath(), PROFILES_SUBDIR_NAME);
        if ( !mProfilesDir.exists() ) {
            boolean succcess = mProfilesDir.mkdir();
            if (succcess) {
                uploadDefaultProfile();
            }
        }
    }

}
