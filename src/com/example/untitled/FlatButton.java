package com.example.untitled;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class FlatButton extends LinearLayout {

    private final Context mContext;

    private boolean mDrawCorner;
    private int mCornerColor;
    private int mTextColor;
    private String mText;
    private Drawable mIconSource;
    private float mTextSize;

    private void setCustomAttributes(Context context, AttributeSet attributes) {
        TypedArray a = context.obtainStyledAttributes(attributes, R.styleable.FlatButton);

        mDrawCorner = a.getBoolean(R.styleable.FlatButton_drawCorner, false);
        mCornerColor = a.getColor(R.styleable.FlatButton_cornerColor, Color.BLACK);
        mIconSource = a.getDrawable(R.styleable.FlatButton_iconSource);
        mText = a.getString(R.styleable.FlatButton_text);
        mTextColor = a.getColor(R.styleable.FlatButton_textColor, Color.BLACK);
        mTextSize = a.getDimension(R.styleable.FlatButton_textSize,
                18.0f * context.getResources().getDisplayMetrics().scaledDensity);
    }

    private void prepareButton() {
        LinearLayout.LayoutParams layoutParams =
                new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(15, 5, 10, 5);

        if (mIconSource != null) {
            ImageView icon = new ImageView(mContext);
            icon.setLayoutParams(layoutParams);
            icon.setImageDrawable(mIconSource);
            addView(icon);
        }

        if (mText != null) {
            TextView text = new TextView(mContext);
            layoutParams.gravity = Gravity.CENTER;
            text.setLayoutParams(layoutParams);
            text.setText(mText);
            text.setTextColor(mTextColor);
            text.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
            text.setTypeface(null, Typeface.BOLD);
            addView(text);
        }
    }

    public FlatButton(Context context) {
        super(context);

        mContext = context;
    }

    public FlatButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        setCustomAttributes(context, attrs);
        prepareButton();
    }

    public FlatButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;

        setCustomAttributes(context, attrs);
        prepareButton();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (mDrawCorner) {
            int x = getWidth(), y = getHeight();
            Path path = new Path();
            path.moveTo(x, y);
            path.lineTo(x - 75, y);
            path.lineTo(x, y - 75);
            path.lineTo(x, y);
            path.close();

            Paint paint = new Paint();
            paint.setColor(mCornerColor);
            canvas.drawPath(path, paint);
        }
    }
}
