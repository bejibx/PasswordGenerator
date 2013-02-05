package com.example.untitled;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.text.*;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class MyActivity extends Activity {

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
    private ArrayList<Category> mCategories = new ArrayList<Category>();

    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 64;


    private int getCurrentPasswordLength() {
        return Integer.decode(mEdit_passwordLength.getText().toString());
    }

    private void loadXML() {
        XmlPullParser parser = getResources().getXml(R.xml.categories);
        try {
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                int currentCategoryIndex = mCategories.size() - 1;
                switch (parser.getEventType()) {
                    case XmlPullParser.START_DOCUMENT:
                    case XmlPullParser.END_TAG:
                    case XmlPullParser.TEXT:
                    default:
                        break;

                    case XmlPullParser.START_TAG:
                        switch (parser.getDepth()) {
                            case  2:
                                if (parser.getName().equals("category")) {
                                    String name = "";
                                    int color = Color.TRANSPARENT;
                                    for (int i = 0; i < parser.getAttributeCount(); i++) {
                                        if ( parser.getAttributeName(i).equals("name") ) {
                                            name = parser.getAttributeValue(i);
                                        } else if (parser.getAttributeName(i).equals("color")) {
                                            try {
                                                color = Color.parseColor(parser.getAttributeValue(i));
                                            } catch (IllegalArgumentException argEx) {
                                                color = Category.DEFAULT_COLOR;
                                            }
                                        }
                                    }
                                    if (color == Color.TRANSPARENT) {
                                        color = Category.DEFAULT_COLOR;
                                    }
                                    if (name.length() > 0) {
                                        mCategories.add(new Category(name, color));
                                    }
                                }
                                break;

                            case 3:
                                if (parser.getName().equals("item") && currentCategoryIndex >= 0) {
                                    Character letter = null;
                                    boolean enabled = Category.Element.DEFAULT_ENABLING;;
                                    for (int i = 0; i < parser.getAttributeCount(); i++) {
                                        if ( parser.getAttributeName(i).equals("letter") ) {
                                            try{
                                                letter = parser.getAttributeValue(i).charAt(0);
                                            } catch (IndexOutOfBoundsException boundsEx) {
                                                //do nothing
                                            }
                                        } else if (parser.getAttributeName(i).equals("enabled")) {
                                            try {
                                                enabled = Boolean.parseBoolean(parser.getAttributeValue(i));
                                            } catch (IllegalArgumentException argEx) {
                                                //do nothing
                                            }
                                        }
                                    }
                                    if (letter != null) {
                                        mCategories.get(currentCategoryIndex).addElement(
                                                new Category.Element(letter, enabled)
                                        );
                                    }
                                }
                                break;

                            default:
                                break;
                        }
                        break;
                }
                parser.next();
            }
        }
        catch (XmlPullParserException xmlEx) {}
        catch (IOException e) {}
    }

    private String getColorForLetter(char letter) {
        for (Category c : mCategories) {
            if (c.containsElement(letter)) {
                return String.format("#%06X", 0xFFFFFF & c.getColor());
            }
        }
        return String.format("#%06X", 0xFFFFFF & Category.DEFAULT_COLOR);
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(mDisplaySize);

        findViewById(R.id.root).setMinimumWidth((int)(mDisplaySize.x * 0.8));

        loadXML();

        mEdit_passwordLength = (EditText)findViewById(R.id.length);
        mEdit_password = (EditText)findViewById(R.id.result);

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
                        String a = getColorForLetter(current);
                        builder
                                .append("<font color=\"")
                                .append(getColorForLetter(current))
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

//        mEdit_password.addTextChangedListener(new TextWatcher() {
//
//            String lastText = null;
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (!s.toString().equals(lastText)) {
//                    lastText = s.toString();
//                    String result = "";
//                    char[] split = s.toString().toCharArray();
//                    for (char c : split) {
//                        String color = null;
//                        if (c == 'a') {
//                            color = "red";
//                        } else if (c == 'b') {
//                            color = "green";
//                        } else if (c == 'c') {
//                            color = "blue";
//                        }
//                        // etc...
//                        if (color != null) {
//                            result += "<font color=\"" + color + "\">" + c + "</font>";
//                        } else {
//                            result += c;
//                        }
//                    }
//                    int selectStart = mEdit_password.getSelectionStart();
//                    int selectEnd = mEdit_password.getSelectionEnd();
//                    mEdit_password.setText(Html.fromHtml(result));
//                    mEdit_password.setSelection(selectStart, selectEnd);
//                }
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
//
//            @Override
//            public void afterTextChanged(Editable editable) {}
//        });


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
}
