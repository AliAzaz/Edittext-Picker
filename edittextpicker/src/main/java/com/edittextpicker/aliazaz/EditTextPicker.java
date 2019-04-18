package com.edittextpicker.aliazaz;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;

public class EditTextPicker extends AppCompatEditText implements TextWatcher {

    private float min, max, floatdefaultvalue;
    private String defaultvalue;
    private String mask;
    private String pattern;
    private Integer type;
    private Boolean required;
    static String TAG = EditTextPicker.class.getName();
    private boolean maskCheckFlag = true;

    public EditTextPicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        ImplementListeners();

        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.EditTextPicker,
                    0, 0
            );

            try {
                //required flag
                required = a.getBoolean(R.styleable.EditTextPicker_required, true);

                //Pattern
                pattern = a.getString(R.styleable.EditTextPicker_pattern);

                //For type -> range and equal
                type = a.getInteger(R.styleable.EditTextPicker_type, 0);
                if (type == 1) {

                    min = a.getFloat(R.styleable.EditTextPicker_minValue, -1);
                    max = a.getFloat(R.styleable.EditTextPicker_maxValue, -1);
                    floatdefaultvalue = a.getFloat(R.styleable.EditTextPicker_defaultValue, -1);

                    if (min == -1)
                        throw new RuntimeException("Min value not provided");
                    if (max == -1)
                        throw new RuntimeException("Max value not provided");

                } else if (type == 2) {

                    defaultvalue = a.getString(R.styleable.EditTextPicker_defaultValue);

                    if (defaultvalue == null)
                        throw new RuntimeException("Default value not provided");
                }

                // For mask
                mask = a.getString(R.styleable.EditTextPicker_mask);
                if (mask != null) {
                    if (!mask.trim().isEmpty()) {
                        maskingEditText(mask);
                    }
                }


            } catch (Exception e) {
                Log.e(TAG, "TextPicker: ", e);
            } finally {
                a.recycle();
            }
        }
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getFloatdefaultvalue() {
        return floatdefaultvalue;
    }

    public void setFloatdefaultvalue(float floatdefaultvalue) {
        this.floatdefaultvalue = floatdefaultvalue;
    }

    public String getDefaultvalue() {
        return defaultvalue;
    }

    public void setDefaultvalue(String defaultvalue) {
        this.defaultvalue = defaultvalue;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    private void ImplementListeners() {
        super.addTextChangedListener(this);
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
        String txt = TextUtils.editTextLoopToNextChar(mask, editable.length() - 1);
        EditTextPicker.super.getText().insert(editable.length() - 1, txt);
    }

    // call for maskingEditText
    private void maskingEditText(String mask) {
        super.setFilters(TextUtils.setLengthEditText(mask)); //Setting length
    }

    // call for checking empty textbox
    public boolean isEmptyTextBox() {
        if (!required) return true;
        if (super.getText().toString().isEmpty()) {
            Log.i(this.getContext().getClass().getName(), this.getContext().getResources().getResourceEntryName(super.getId()) + ": Empty!!");
            super.setError("Required! ");
            super.setFocusableInTouchMode(true);
            super.requestFocus();
            invalidate();
            return false;
        }
        return true;
    }

    // call for checking range textbox
    public boolean isRangeTextValidate() {
        if (!required) return true;
        if (!isEmptyTextBox()) return false;
        if (!checkingPattern()) return false;

        if (Float.valueOf(super.getText().toString()) < min || Float.valueOf(super.getText().toString()) > max) {
            if (floatdefaultvalue != -1) {
                Float dValue = Float.parseFloat(super.getText().toString());
                if (Float.parseFloat(super.getText().toString()) == Math.round(Float.parseFloat(super.getText().toString())))
                    dValue = Float.parseFloat(super.getText().toString().split("\\.")[0]);

                if (dValue.equals(floatdefaultvalue)) {
                    invalidate();
                    return true;
                }
            }
            String minVal = String.valueOf(min);
            String maxVal = String.valueOf(max);

            if (min == Math.round(min))
                minVal = (minVal.split("\\.")[0]);

            if (max == Math.round(max))
                maxVal = (maxVal.split("\\.")[0]);

            super.setError("Range is " + minVal + " to " + maxVal + " !!");
            super.setFocusableInTouchMode(true);
            super.requestFocus();
            Log.i(this.getContext().getClass().getName(), this.getContext().getResources().getResourceEntryName(super.getId()) + ": Range is " + minVal + " to " + maxVal + "!!");
            invalidate();
            return false;
        }
        return true;
    }

    // call for checking default value in textbox
    public boolean isTextEqualToPattern() {
        if (!required) return true;
        if (!isEmptyTextBox()) return false;
        if (!checkingPattern()) {
            if (!super.getText().toString().equals(String.valueOf(defaultvalue))) return false;
        }
        super.setError(null);
        super.clearFocus();
        invalidate();
        return true;
    }

    public boolean checkingPattern() {
        if (pattern == null) return true;
        if (!super.getText().toString().matches(pattern)) {
            super.setError("Not match to pattern!!");
            super.setFocusableInTouchMode(true);
            super.requestFocus();
            Log.i(this.getContext().getClass().getName(), this.getContext().getResources().getResourceEntryName(super.getId()) + ": Not match to pattern!!");
            invalidate();
            return false;
        }
        return true;
    }

}
