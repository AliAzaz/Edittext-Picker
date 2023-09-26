package com.edittextpicker.aliazaz.utils

import com.edittextpicker.aliazaz.EditTextPicker


/*
* @author Ali Azaz Alam
* */
interface EditTextViews {

    fun setMask(mask: String): EditTextPicker

    fun setRequired(required: Boolean): EditTextPicker

    fun setPattern(pattern: String): EditTextPicker

    fun setMinValue(minValue: Float): EditTextPicker

    fun setMaxValue(maxValue: Float): EditTextPicker

    fun setRangeDefaultValue(defaultValue: Float): EditTextPicker

    fun setEqualDefaultValue(defaultValue: String): EditTextPicker

    fun setMaskText(text: String)

}