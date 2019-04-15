package com.edittextpicker.aliazaz;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;

public class EditTextPicker extends AppCompatEditText implements TextWatcher {

    private float minvalue, maxvalue;
    private Object defaultvalue;
    private String mask;
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

                //For type -> range and equal
                type = a.getInteger(R.styleable.EditTextPicker_type, 0);
                if (type == 1) {

                    minvalue = a.getFloat(R.styleable.EditTextPicker_minValue, -1);
                    maxvalue = a.getFloat(R.styleable.EditTextPicker_maxValue, -1);
                    defaultvalue = a.getFloat(R.styleable.EditTextPicker_defaultValue, -1);

                    if (minvalue == -1)
                        throw new RuntimeException("Min value not provided");
                    if (maxvalue == -1)
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

    private void ImplementListeners() {
        super.addTextChangedListener(this);
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

    public Object getDefaultvalue() {
        return defaultvalue;
    }

    public void setDefaultvalue(Object defaultvalue) {
        this.defaultvalue = defaultvalue;
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

        if (!required)
            return true;

        if (super.getText().toString().isEmpty()) {
            Log.i(this.getContext().getClass().getName(), this.getContext().getResources().getResourceEntryName(super.getId()) + ": Empty!!");
            super.setError("Required! ");
            super.setFocusableInTouchMode(true);
            super.requestFocus();

            invalidate();
            requestLayout();

            return false;
        }

        return true;
    }

    // call for checking range textbox
    public boolean isRangeTextValidate() {

        if (!required)
            return true;

        if (!isEmptyTextBox())
            return false;

        if (Float.valueOf(super.getText().toString()) < minvalue || Float.valueOf(super.getText().toString()) > maxvalue) {

            if ((Float) defaultvalue != -1) {

                String dValue = String.valueOf(defaultvalue);
                if ((Float) defaultvalue == Math.round((Float) defaultvalue))
                    dValue = (dValue.split("\\.")[0]);

                boolean flag = (super.getText().toString().equals(String.valueOf(dValue)));

                invalidate();
                requestLayout();

                if (flag)
                    return true;
            }

            String min = String.valueOf(minvalue);
            String max = String.valueOf(maxvalue);

            if (minvalue == Math.round(minvalue))
                min = (min.split("\\.")[0]);

            if (maxvalue == Math.round(maxvalue))
                max = (max.split("\\.")[0]);

            super.setError("Range is " + min + " to " + max + " !!");
            super.setFocusableInTouchMode(true);
            super.requestFocus();
            Log.i(this.getContext().getClass().getName(), this.getContext().getResources().getResourceEntryName(super.getId()) + ": Range is " + min + " to " + max + "!!");

            invalidate();
            requestLayout();

            return false;
        }

        return true;
    }

    // call for checking default value in textbox
    public boolean isTextEqual() {

        if (!required)
            return true;

        if (!isEmptyTextBox())
            return false;

        if (!super.getText().toString().equals(String.valueOf(defaultvalue))) {

            super.setError("Not equal to default value: " + defaultvalue + " !!");
            super.setFocusableInTouchMode(true);
            super.requestFocus();
            Log.i(this.getContext().getClass().getName(), this.getContext().getResources().getResourceEntryName(super.getId()) + ": Not Equal to default value: " + defaultvalue + "!!");

            invalidate();
            requestLayout();

            return false;
        }

        return true;
    }

}
