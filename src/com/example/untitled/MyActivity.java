package com.example.untitled;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.NumberPicker;

public class MyActivity extends Activity {

    private CategoryAdapter adapter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


//        adapter = new CategoryAdapter(this);
//
//        adapter.addCategory(new Category(
//            getString(R.string.uppercaseLetters),
//            new GridElementAdapter(this, "ABCDEFGHIJKLMNOPQRSTUVWXYZ")
//        ));
//
//        adapter.addCategory(new Category(
//                getString(R.string.lowercaseLetters),
//                new GridElementAdapter(this, "abcdefghijklmnopqrstuvwxyz")
//        ));
//
//        adapter.attachToView((ViewGroup) findViewById(R.id.categories));
    }
}
