package com.edittextpicker.aliazaz.textpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;

public class TextPicker extends AppCompatEditText {

    private float minValue, maxValue, defaultValue;
    private Context mContext;
    private String msg, mask;
    private Integer type;
    private Boolean reqFlag;

    public TextPicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.TextPicker,
                    0, 0
            );

            try {
                //required flag
                reqFlag = a.getBoolean(R.styleable.TextPicker_required, false);

                //For type -> range and equal
                type = a.getInteger(R.styleable.TextPicker_type, 0);
                if (type == 1) {

                    minValue = a.getFloat(R.styleable.TextPicker_minValue, -1);
                    maxValue = a.getFloat(R.styleable.TextPicker_maxValue, -1);
                    defaultValue = a.getFloat(R.styleable.TextPicker_defaultValue, -1);

                    if (minValue == -1)
                        throw new RuntimeException("Min value not provided");
                    if (maxValue == -1)
                        throw new RuntimeException("Max value not provided");

                } else if (type == 2) {
                    defaultValue = a.getFloat(R.styleable.TextPicker_defaultValue, -1);

                    if (defaultValue == -1)
                        throw new RuntimeException("Default value not provided");
                }

                // For mask
                mask = a.getString(R.styleable.TextPicker_mask);


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

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
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

        if (Float.valueOf(super.getText().toString()) < minValue || Float.valueOf(super.getText().toString()) > maxValue) {

            String min = String.valueOf(minValue);
            String max = String.valueOf(maxValue);

            if (minValue != Math.round(minValue))
                min = (min.split(".")[0]);

            if (maxValue != Math.round(maxValue))
                max = (min.split(".")[0]);

            super.setError("Range is " + min + " to " + max + " " + msg + " !!");
            super.setFocusableInTouchMode(true);
            super.requestFocus();
            Log.i(mContext.getClass().getName(), mContext.getResources().getResourceEntryName(super.getId()) + ": Range is " + min + " to " + max + "!!");

            invalidate();
            requestLayout();

            if (defaultValue != -1)
                return (super.getText().toString().equals(String.valueOf(defaultValue)));

            return false;
        } else {
            super.setError(null);
            super.clearFocus();

            invalidate();
            requestLayout();

            return true;
        }
    }

    public boolean isTextEqual() {

        if (!isEmptyTextBox())
            return false;

        if (super.getText().toString().equals(String.valueOf(defaultValue))) {

            super.setError("Not equal to default value: " + defaultValue + " !!");
            super.setFocusableInTouchMode(true);
            super.requestFocus();
            Log.i(mContext.getClass().getName(), mContext.getResources().getResourceEntryName(super.getId()) + ": Not Equal to default value: " + defaultValue + "!!");

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
