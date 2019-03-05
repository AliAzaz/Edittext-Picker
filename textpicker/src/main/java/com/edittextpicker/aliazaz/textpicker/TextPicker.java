package com.edittextpicker.aliazaz.textpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;

public class TextPicker extends AppCompatEditText implements TextWatcher {

    private float minValue, maxValue, defaultValue;
    private Context mContext;
    private String msg, mask;
    private Integer type;
    private Boolean reqFlag;
    static String TAG = TextPicker.class.getName();
    private boolean maskCheckFlag = true;

    public TextPicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        ImplementListeners();

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
                if (!mask.trim().isEmpty()) {
                    maskingEditText(mask);
                }


            } finally {
                a.recycle();
            }
        }
    }

    private void ImplementListeners() {
        super.addTextChangedListener(this);
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

    private void maskingEditText(final String mask) {
        super.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mask.length())}); //Setting length
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (mask == null) return;
        maskCheckFlag = i2 != 0;
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (mask == null) return;
        if (!maskCheckFlag) return;
        String txt = editTextLoopToNextChar(mask, editable.length() - 1);
        TextPicker.super.getText().insert(editable.length() - 1, txt);
    }

    private String editTextLoopToNextChar(String maskEdit, int position) {

        String finalResult = "";
        for (int i = position; i < maskEdit.length(); i++) {
            if (maskEdit.charAt(i) != '#') {
                finalResult += maskEdit.charAt(i);
            } else
                break;
        }

        return finalResult;
    }


}
