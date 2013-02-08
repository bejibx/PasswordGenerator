package com.example.untitled;

import android.content.Context;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.Collection;

public class GridElementAdapter extends BaseAdapter {

    private final Context mContext;
    private final Category.Element[] mElements;
    private final LayoutInflater mInflater;
    private final String mSpaceLabel;
    private final float mSpaceLabelTextSize;

    private float calculateTextWidth(String text, float containerWidth) {
        float hi = 100;
        float lo = 2;
        final float threshold = 0.5f;

        Paint mTestPaint = new Paint();

        while((hi - lo) > threshold) {
            float size = (hi + lo) / 2;
            mTestPaint.setTextSize(size);
            if (mTestPaint.measureText(text) >= containerWidth) {
                hi = size; // too big
            } else {
                lo = size; // too small
            }
        }

        return lo;
    }

    public GridElementAdapter(Context context, Collection<Category.Element> elements) {
        mElements = elements.toArray(new Category.Element[elements.size()]);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;

        if (elements.contains(new Category.Element(' '))) {
            mSpaceLabel = mContext.getString(R.string.space);

            View view = mInflater.inflate(R.layout.grid_element, null, false);
            float targetWidth = mContext.getResources().getDimension(R.dimen.gridCellSize)
                    - view.getPaddingLeft() - view.getPaddingRight();

            mSpaceLabelTextSize = calculateTextWidth(mSpaceLabel, targetWidth);
        } else {
            mSpaceLabel = "";
            mSpaceLabelTextSize = 0.0f;
        }
    }

    @Override
    public int getCount() {
        return mElements.length;
    }

    @Override
    public Object getItem(int i) {
        return mElements[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Button result;
        if (view == null) {
            result = (Button) mInflater.inflate(R.layout.grid_element, viewGroup, false);
        } else {
            result = (Button) view;
        }

        if (mElements[i].letter != ' ') {
            result.setText(String.valueOf(mElements[i].letter));
        } else {
            result.setText(mSpaceLabel);
            result.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSpaceLabelTextSize);
        }
        return result;
    }


}
