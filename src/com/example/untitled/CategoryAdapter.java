package com.example.untitled;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class CategoryAdapter {

    private final LayoutInflater mInflater;
    private final Context mContext;

    private ArrayList<Category> mCategories = new ArrayList<Category>();

    private String getLocalizedName(String categoryName) {
        if (categoryName.equals("Uppercase")) {
            return mContext.getString(R.string.uppercaseLetters);
        } else if (categoryName.equals("Lowercase")) {
            return mContext.getString(R.string.lowercaseLetters);
        } else if (categoryName.equals("Numbers")) {
            return mContext.getString(R.string.numbers);
        } else if (categoryName.equals("Punctuation")) {
            return mContext.getString(R.string.punctuationMarks);
        } else if (categoryName.equals("Typography")) {
            return mContext.getString(R.string.typographyMarks);
        } else {
            return categoryName;
        }
    }

    public CategoryAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addCategory(Category category) {
        mCategories.add(category);
    }

    public void initializeFromXML(XmlPullParser xpp) {
        XmlPullParser parser = xpp;//getResources().getXml(R.xml.categories);
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
                                                Color.parseColor(parser.getAttributeValue(i));
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
                                        mCategories.add(new Category(getLocalizedName(name), color));
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

    public void attachToView(ViewGroup parent) {
        for (Category c : mCategories) {
            View header = mInflater.inflate(R.layout.category_header, parent, false);
            ((TextView) header.findViewById(R.id.Text)).setText(c.getName());
            parent.addView(header);
            GridView grid = (GridView) mInflater.inflate(R.layout.category_grid, parent, false);
            grid.setAdapter(new GridElementAdapter(mContext, c.elements()));
            parent.addView(grid);
        }
    }

    public int getColorForLetter(char letter) {
        for (Category c : mCategories) {
            if (c.containsElement(letter)) {
                return c.getColor();
            }
        }
        return Category.DEFAULT_COLOR;
    }

}
