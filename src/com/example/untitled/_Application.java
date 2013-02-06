package com.example.untitled;

import android.app.Application;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

public class _Application extends Application {

    private CategoryAdapter mCategoryAdapter;

    private XmlPullParser getDefaultXml() {
        XmlPullParserFactory factory;
        XmlPullParser result = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setValidating(false);
            result = factory.newPullParser();
            InputStream raw = getAssets().open(SymbolProfiles.DEFAULT_PROFILE);
            result.setInput(raw, null);
        } catch (XmlPullParserException e) {
            //do nothing
        } catch (IOException e) {
            //do nothing
        }
        return result;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mCategoryAdapter = new CategoryAdapter(this);

        mCategoryAdapter.initializeFromXML( getDefaultXml() );

        new SymbolProfiles(this);
    }

    public CategoryAdapter getAdapter() {
        return mCategoryAdapter;
    }
}
