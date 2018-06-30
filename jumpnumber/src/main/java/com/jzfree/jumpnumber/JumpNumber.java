package com.jzfree.jumpnumber;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Wang Jiuzhou on 2018/6/29 17:31
 */
public class JumpNumber extends View {
    private Integer number = 0;
    private int textColor;
    private float textSize;
    private String unChangedNumber = "";
    private String changedOldNumber = "";
    private String changedNewNumber = "";
    private float offset = 1;
    private Paint mPaint;
    private boolean isPlus;
    private ObjectAnimator animator;

    public JumpNumber(Context context) {
        this(context, null);
    }

    public JumpNumber(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JumpNumber(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.JumpNumber);
        number = array.getInteger(R.styleable.JumpNumber_number, 0);
        textColor = array.getColor(R.styleable.JumpNumber_textColor, Color.BLACK);
        textSize = array.getDimensionPixelSize(R.styleable.JumpNumber_textSize, 24);
        array.recycle();

        init();
    }

    private void init() {
        animator = initAnim();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(textSize);
        mPaint.setColor(textColor);

        unChangedNumber = String.valueOf(number);
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        mPaint.setTextSize(textSize);
    }

    public void setTextColor(int color) {
        this.textColor = color;
        mPaint.setColor(textColor);
    }

    public void setNumber(int number) {
        this.number = number;
        parseNumber(0);
        animator.end();
        invalidate();
    }

    public int getNumber() {
        return number;
    }

    public boolean plus() {
        if (animator.isRunning()) {
            return false;
        }

        parseNumber(1);
        animator.start();
        return true;
    }

    public boolean minus() {
        if (animator.isRunning()) {
            return false;
        }

        parseNumber(-1);
        animator.start();
        return true;
    }

    public void setOffset(float offset) {
        this.offset = offset;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setAlpha(255);

        canvas.drawText(unChangedNumber, 0, textSize * 2, mPaint);

        mPaint.setAlpha((int) (255 * (1 - offset)));
        canvas.drawText(changedOldNumber, unChangedNumber.length() * textSize * 0.58f, textSize * 2 - (isPlus ? offset : -offset) * textSize, mPaint);

        mPaint.setAlpha((int) (255 * offset));
        canvas.drawText(changedNewNumber, unChangedNumber.length() * textSize * 0.58f, textSize * (isPlus ? 3 : 1) - (isPlus ? offset : -offset) * textSize, mPaint);
    }

    private void parseNumber(int plus) {
        isPlus = plus > 0;
        if (plus == 0) {
            unChangedNumber = String.valueOf(number);
            changedOldNumber = "";
            changedNewNumber = "";

        } else {
            String oldNumber = String.valueOf(number);
            number = number + plus;
            String newNumber = String.valueOf(number);
            for (int i = 0; i < oldNumber.length(); i++) {
                char oldChar = oldNumber.charAt(i);
                char newChar = newNumber.charAt(i);
                if (oldChar != newChar) {
                    unChangedNumber = oldNumber.substring(0, i);
                    changedOldNumber = oldNumber.substring(i);
                    changedNewNumber = newNumber.substring(i);
                    break;
                }
            }
        }
    }

    private ObjectAnimator initAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "offset", 0, 1);
        animator.setDuration(500);
        return animator;
    }
}
