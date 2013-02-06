package com.example.untitled;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.*;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class _Main extends Activity {

//    public class PasswordLengthFilter implements InputFilter {
//
//        private final int mMinValue, mMaxValue;
//
//        public PasswordLengthFilter(int min, int max) {
//            mMinValue = min;
//            mMaxValue = max;
//        }
//
//        @Override
//        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//            StringBuilder result = new StringBuilder(dest);
//            result.replace(dstart, dend, source.toString());
//
//            if (result.length() == 0) {
//                return String.valueOf(MIN_LENGTH);
//            } else {
//                String maxValue = String.valueOf(mMaxValue);
//                if (result.length() > maxValue.length()) {
//                    return null;
//                }
//            } .length()){
//                return S
//            } else {
//                try {
//                    int isInRange = isInRange( Integer.parseInt(result.toString()) );
//
//                    if (isInRange != 0) {
//                        return String.valueOf(MAX_LENGTH).substring(dstart, dend + 1);
//                    } else {
//                        return null;
//                    }
//                } catch (NumberFormatException nfe) { }
//            }
//
//            return "";
//        }
//
//        private int isInRange(int input) {
//            if (input > mMaxValue) {
//                return 1;
//            } else if (input < mMinValue) {
//                return -1;
//            } else {
//                return 0;
//            }
//        }
//    }

    private EditText mEdit_passwordLength;
    private EditText mEdit_password;

    private Point mDisplaySize = new Point();
    private CategoryAdapter mCategoryAdapter;

    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 64;


    private int getCurrentPasswordLength() {
        return Integer.decode(mEdit_passwordLength.getText().toString());
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._main);

        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(mDisplaySize);
        mCategoryAdapter = ((_Application)getApplication()).getAdapter();

        mEdit_passwordLength = (EditText)findViewById(R.id.length);
        mEdit_password = (EditText)findViewById(R.id.result);


        findViewById(R.id.root).setMinimumWidth((int)(mDisplaySize.x * 0.8));

        mEdit_passwordLength.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    int value = Integer.parseInt(editable.toString());
                    if (value > MAX_LENGTH) {
                        mEdit_passwordLength.setText(String.valueOf(MAX_LENGTH));
                        mEdit_passwordLength.setSelection(0, editable.length() - 1);
                        mEdit_passwordLength.setError(getString(
                                R.string.error_maxLength,
                                MAX_LENGTH,
                                getString(R.string.error_end)
                        ));
                    } else if (value < MIN_LENGTH) {
                        mEdit_passwordLength.setText(String.valueOf(MIN_LENGTH));
                        mEdit_passwordLength.setSelection(0, editable.length() - 1);
                        mEdit_passwordLength.setError(getString(
                                R.string.error_minLength,
                                MIN_LENGTH,
                                ""
                        ));
                    }
                } catch (NumberFormatException nfe) {
                    mEdit_passwordLength.setText(String.valueOf(MIN_LENGTH));
                    mEdit_passwordLength.setSelection(editable.length() - 1);
                }
            }
        });

        mEdit_password.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.length() > 0) {
                    StringBuilder builder = new StringBuilder();
                    for (int i = start; i < end; i++) {
                        char current = source.charAt(i);
                        builder
                                .append("<font color=\"")
                                .append(mCategoryAdapter.getColorForLetter(current))
                                .append("\">")
                                .append(current)
                                .append("</font>");


                    }
                    return Html.fromHtml(builder.toString());
                } else {
                    return null;
                }
            }
        }});

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        LinearLayout menu = (LinearLayout) findViewById(R.id.menu);

        int width = menu.getWidth();
        if (width > mDisplaySize.x - 80) {
            menu.setOrientation(LinearLayout.VERTICAL);
        }
    }

    @SuppressWarnings("UnusedParameters")
    public void onPlusClick(View source) {
        int length = getCurrentPasswordLength();
        if (length < 64) {
            mEdit_passwordLength.setText(String.valueOf(length + 1));
        }
    }

    @SuppressWarnings("UnusedParameters")
    public void onMinusClick(View source) {
        int length = getCurrentPasswordLength();
        if (length > 1) {
            mEdit_passwordLength.setText(String.valueOf(length - 1));
        }
    }

    @SuppressWarnings("UnusedParameters")
    public void onAvailableSymbolsClick(View source) {
        startActivity(new Intent(this, _AvailableSymbols.class));
    }
}
