package com.example.untitled;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FlatButton extends LinearLayout {

    public static final int TOP_LEFT = 0;
    public static final int TOP_RIGHT = 1;
    public static final int BOTTOM_LEFT = 2;
    public static final int BOTTOM_RIGHT = 3;


    private final Context mContext;

    private boolean mDrawCorner;
    private int mCornerColor;
    private int mTextColor;
    private String mText;
    private Drawable mIconSource;
    private float mTextSize;
    private int mDirection;
    private int mLength;
    private int mCornerColorPressed;

    private int mColorBuffer;

    private void setCustomAttributes(Context context, AttributeSet attributes) {
        TypedArray a = context.obtainStyledAttributes(attributes, R.styleable.FlatButton);

        mDrawCorner = a.getBoolean(R.styleable.FlatButton_drawCorner, false);
        mCornerColor = a.getColor(R.styleable.FlatButton_cornerColor, Color.BLACK);
        mIconSource = a.getDrawable(R.styleable.FlatButton_iconSource);
        mText = a.getString(R.styleable.FlatButton_text);
        mTextColor = a.getColor(R.styleable.FlatButton_textColor, Color.BLACK);
        mTextSize = a.getDimension(R.styleable.FlatButton_textSize,
                18.0f * context.getResources().getDisplayMetrics().scaledDensity);
        mDirection = a.getInteger(R.styleable.FlatButton_direction, 3);
        mLength = (int) a.getDimension(R.styleable.FlatButton_cornerHeight, 60);
        mCornerColorPressed = a.getColor(R.styleable.FlatButton_cornerColorPressed, mCornerColor);
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
    protected void onDraw(Canvas canvas) {
        mColorBuffer = isPressed() ? mCornerColorPressed : mCornerColor;
        super.onDraw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (mDrawCorner) {
            Point start = new Point();
            Point move = new Point();

            switch (mDirection) {
                case TOP_LEFT:
                    start.x = 0;
                    start.y = 0;
                    move.x = mLength;
                    move.y = mLength;
                    break;

                case TOP_RIGHT:
                    start.x = getWidth();
                    start.y = 0;
                    move.x = getWidth() - mLength;
                    move.y = mLength;
                    break;

                case BOTTOM_LEFT:
                    start.x = 0;
                    start.y = getHeight();
                    move.x = mLength;
                    move.y = getHeight() - mLength;
                    break;

                default:
                case BOTTOM_RIGHT:
                    start.x = getWidth();
                    start.y = getHeight();
                    move.x = getWidth() - mLength;
                    move.y = getHeight() - mLength;
                    break;
            }

            Path path = new Path();
            path.moveTo(start.x, start.y);
            path.lineTo(move.x, start.y);
            path.lineTo(start.x, move.y);
            path.lineTo(start.x, start.y);
            path.close();

            Paint paint = new Paint();
            paint.setColor(mColorBuffer);
            canvas.drawPath(path, paint);
        }
    }
}
