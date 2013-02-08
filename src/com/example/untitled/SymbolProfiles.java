package com.example.untitled;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.*;

public class SymbolProfiles {

    private static final String PROFILES_SUBDIR_NAME = "profiles";
    public static final String DEFAULT_PROFILE = "default.xml";
    public static final String SHARED_PREFERENCES = "preferences";
    public static final String KEY_CURRENT_PROFILE = "CurrentProfile"; 

    private final Context mContext;
    private final File mProfilesDir;
    private final SharedPreferences mPreferences;
    
    private CategoryAdapter mCategoryAdapter;
    private boolean mIsActive = false;
    private String mCurrentProfileName = "";

    private XmlPullParser getParserForStream(InputStream stream) throws XmlPullParserException {
        XmlPullParserFactory factory;
        XmlPullParser result = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setValidating(false);
            result = factory.newPullParser();
            result.setInput(stream, null);
        } catch (XmlPullParserException e) {
            //do nothing
        }
        return result;
    }

    private XmlPullParser getDefaultXml() throws IOException, XmlPullParserException {
        return getParserForStream(mContext.getAssets().open(SymbolProfiles.DEFAULT_PROFILE));
    }

    private XmlPullParser getParserForFile(File file) throws FileNotFoundException, XmlPullParserException {
        return getParserForStream(new FileInputStream(file));
    }
    
    private void uploadDefaultProfile() throws IOException {
        AssetManager assetManager = mContext.getResources().getAssets();

        File output = new File(mProfilesDir.getPath(), DEFAULT_PROFILE);
        InputStream is;

        is = assetManager.open(DEFAULT_PROFILE);
        OutputStream os = new FileOutputStream(output, true);
        final int buffer_size = 1024/* * 1024*/;
        byte[] bytes = new byte[buffer_size];
        int bytesRead;

        while ((bytesRead = is.read(bytes, 0, buffer_size)) != -1) {
            os.write(bytes, 0, bytesRead);
        }

        is.close();
        os.close();
    }

    private void resetProfile() {
        mCurrentProfileName = DEFAULT_PROFILE;
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(KEY_CURRENT_PROFILE, mCurrentProfileName);
        editor.commit();
    }

    public SymbolProfiles(Context context) {
        mContext = context;
        mPreferences = mContext.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        mProfilesDir = new File(mContext.getFilesDir().getPath(), PROFILES_SUBDIR_NAME);
        mCategoryAdapter = new CategoryAdapter(mContext);

        if ( !mProfilesDir.exists() ) {
            boolean success = mProfilesDir.mkdir();
            if (success) {
                try {
                    uploadDefaultProfile();
                    resetProfile();
                    mIsActive = true;
                } catch (IOException e) {
                    //do nothing
                }
            }
        } else {
            if (mPreferences.contains(KEY_CURRENT_PROFILE)) {
                mCurrentProfileName = mPreferences.getString(KEY_CURRENT_PROFILE, DEFAULT_PROFILE);

                if (!mCurrentProfileName.endsWith(".xml")) {
                    mCurrentProfileName += ".xml";
                }
            } else {
                resetProfile();
            }

            File currentProfile = new File(mProfilesDir.getPath(), mCurrentProfileName);
            try {
                if (currentProfile.exists()) {
                    mCategoryAdapter.initializeFromXML( getParserForFile(currentProfile) );
                    mIsActive = true;
                } else {
                    mCategoryAdapter.initializeFromXML( getDefaultXml() );
                }
            } catch (IOException ioEx) {
                //TODO обработать
            } catch (XmlPullParserException xppEx) {
                //TODO обработать
            }
        }
    }

    public CategoryAdapter getCategories() {
        return mCategoryAdapter;
    }


}
