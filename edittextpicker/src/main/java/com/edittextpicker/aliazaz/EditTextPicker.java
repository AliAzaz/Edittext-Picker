package com.edittextpicker.aliazaz;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;

public class EditTextPicker extends AppCompatEditText implements TextWatcher {

    private float minvalue, maxvalue, rangedefaultvalue;
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

                // For mask
                mask = a.getString(R.styleable.EditTextPicker_mask);
                if (mask != null) {
                    if (!mask.trim().isEmpty()) {
                        maskingEditText(mask);
                    }
                } else
                    EditTextPicker.super.removeTextChangedListener(this);

                //For type -> range and equal
                type = a.getInteger(R.styleable.EditTextPicker_type, 0);
                if (type == 1) {

                    minvalue = a.getFloat(R.styleable.EditTextPicker_minValue, -1);
                    maxvalue = a.getFloat(R.styleable.EditTextPicker_maxValue, -1);
                    rangedefaultvalue = a.getFloat(R.styleable.EditTextPicker_defaultValue, -1);

                    if (minvalue == -1)
                        throw new RuntimeException("Min value not provided");
                    if (maxvalue == -1)
                        throw new RuntimeException("Max value not provided");

                } else if (type == 2) {

                    defaultvalue = a.getString(R.styleable.EditTextPicker_defaultValue);

                    if (defaultvalue == null)
                        throw new RuntimeException("Default value not provided");
                }

            } catch (Exception e) {
                Log.e(TAG, "TextPicker: ", e);
                throw e;
            } finally {
                a.recycle();
            }
        }
    }

    public float getMinvalue() {
        return minvalue;
    }

    public void setMinvalue(float minvalue) {
        this.minvalue = minvalue;
    }

    public float getMaxvalue() {
        return maxvalue;
    }

    public void setMaxvalue(float maxvalue) {
        this.maxvalue = maxvalue;
    }

    public float getRangedefaultvalue() {
        return rangedefaultvalue;
    }

    public void setRangedefaultvalue(float rangedefaultvalue) {
        this.rangedefaultvalue = rangedefaultvalue;
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
        StringBuilder txt = TextUtils.editTextLoopToNextChar(mask, editable.length() - 1);
        if (txt.length() == 0) return;
        //Input Filter work
        InputFilter[] filters = editable.getFilters(); //get filter
        editable.setFilters(new InputFilter[]{}); //reset filter
        editable.insert(editable.length() - 1, txt);
        editable.setFilters(filters); //restore filter
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
        if (type != 1) return true;
        if (!required) return true;
        if (!isEmptyTextBox()) return false;
        if (!checkingTextPattern()) return false;

        if (Float.valueOf(super.getText().toString()) < minvalue || Float.valueOf(super.getText().toString()) > maxvalue) {
            if (rangedefaultvalue != -1) {
                Float dValue = Float.parseFloat(super.getText().toString());
                if (Float.parseFloat(super.getText().toString()) == Math.round(Float.parseFloat(super.getText().toString())))
                    dValue = Float.parseFloat(super.getText().toString().split("\\.")[0]);

                if (dValue.equals(rangedefaultvalue)) {
                    invalidate();
                    return true;
                }
            }
            String minVal = String.valueOf(minvalue);
            String maxVal = String.valueOf(maxvalue);

            if (minvalue == Math.round(minvalue))
                minVal = (minVal.split("\\.")[0]);

            if (maxvalue == Math.round(maxvalue))
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
        if (!checkingTextPattern())
            if (type == 2) {
                if (!super.getText().toString().equals(String.valueOf(defaultvalue))) return false;
            } else return false;
        super.setError(null);
        super.clearFocus();
        invalidate();
        return true;
    }

    private boolean checkingTextPattern() {
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
