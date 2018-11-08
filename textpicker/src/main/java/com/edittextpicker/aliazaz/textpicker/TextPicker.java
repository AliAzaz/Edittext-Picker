package com.edittextpicker.aliazaz.textpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;

public class TextPicker extends AppCompatEditText {

    private float minValue, maxvalue;
    private Context mContext;
    private String msg;
    Integer type;
    Boolean reqFlag;

    public TextPicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.TextPicker,
                    0, 0
            );

            try {
                reqFlag = a.getBoolean(R.styleable.TextPicker_required, false);
                type = a.getInteger(R.styleable.TextPicker_type, 0);
                //For range
                if (type == 2) {
                    minValue = a.getFloat(R.styleable.TextPicker_minValue, -1);
                    maxvalue = a.getFloat(R.styleable.TextPicker_maxValue, -1);
                    if (minValue == -1)
                        throw new RuntimeException("Min value not provided");
                    if (maxvalue == -1)
                        throw new RuntimeException("Max value not provided");
                }
            } finally {
                a.recycle();
            }
        }
    }

    public void setManager(@NonNull Context mContext, @NonNull String msg) {
        this.mContext = mContext;
        this.msg = msg;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public float getMinValue() {
        return minValue;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    public float getMaxvalue() {
        return maxvalue;
    }

    public void setMaxvalue(float maxvalue) {
        this.maxvalue = maxvalue;
    }

    public boolean isEmptyTextBox() {

        if (!reqFlag)
            return true;

        if (super.getText().toString().isEmpty()) {
            Log.i(mContext.getClass().getName(), mContext.getResources().getResourceEntryName(super.getId()) + ": Empty!!");
            super.setError("This data is Required! ");
            super.setFocusableInTouchMode(true);
            super.requestFocus();

            invalidate();
            requestLayout();

            return false;
        }

        return true;
    }

    public boolean isRangeTextValidate() {

        if (!isEmptyTextBox())
            return false;

        if (Double.valueOf(super.getText().toString()) < minValue || Double.valueOf(super.getText().toString()) > maxvalue) {
            super.setError("Range is " + minValue + " to " + maxvalue + " " + msg + " ... ");
            super.setFocusableInTouchMode(true);
            super.requestFocus();
            Log.i(mContext.getClass().getName(), mContext.getResources().getResourceEntryName(super.getId()) + ": Range is " + minValue + " to " + maxvalue + " times...  ");

            invalidate();
            requestLayout();

            return false;
        } else {
            super.setError(null);
            super.clearFocus();

            invalidate();
            requestLayout();

            return true;
        }
    }
}
