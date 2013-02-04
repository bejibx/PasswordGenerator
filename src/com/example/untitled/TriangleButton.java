package com.example.untitled;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

public class TriangleButton extends View {

    private int mDirection;

    private void setCustomAttributes(Context context, AttributeSet attributes) {
        TypedArray a = context.obtainStyledAttributes(attributes, R.styleable.TriangleButton);

        mDirection = a.getInteger(R.styleable.TriangleButton_direction, 0);
    }

    public TriangleButton(Context context) {
        super(context);
    }

    public TriangleButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        setCustomAttributes(context, attrs);
    }

    public TriangleButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setCustomAttributes(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Point start = new Point();
        Point move = new Point();

        switch (mDirection) {
            case 0:
                start.x = 0;
                start.y = 0;
                move.x = getWidth();
                move.y = getHeight();
                break;

            case 1:
                start.x = getWidth();
                start.y = 0;
                move.x = 0;
                move.y = getHeight();
                break;

            case 2:
                start.x = 0;
                start.y = getHeight();
                move.x = getWidth();
                move.y = 0;
                break;

            case 3:
                start.x = getWidth();
                start.y = getHeight();
                move.x = 0;
                move.y = 0;
                break;
        }

        Path path = new Path();
        path.moveTo(start.x, start.y);
        path.lineTo(move.x, start.y);
        path.lineTo(start.x, move.y);
        path.lineTo(start.x, start.y);
        path.close();

        Paint paint = new Paint();

        paint.setColor(isPressed() ? Color.BLUE : Color.RED);
        canvas.drawPath(path, paint);
        //super.onDraw(canvas);
    }
}
