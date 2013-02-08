package com.example.untitled;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

public class _AvailableSymbols extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout._available_symbols);

       ((_Application)getApplication()).getProfiller().getCategories()
               .attachToView((ViewGroup)findViewById(R.id.categories));
    }
}
