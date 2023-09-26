package com.edittextpicker.aliazaz.repository

import com.edittextpicker.aliazaz.model.EditTextModel
import com.edittextpicker.aliazaz.model.EditTextPickerModel
import com.edittextpicker.aliazaz.utils.CONSTANTS
import org.apache.commons.lang3.StringUtils

/*
* @author Ali Azaz Alam
* */
class EditTextPickerBuilder {

    private val editTextPickerModel: EditTextPickerModel by lazy {
        EditTextPickerModel()
    }

    @JvmOverloads
    fun setRangeValues(
        minValue: Float,
        maxValue: Float,
        defaultValue: Float = -1f
    ): EditTextPickerBuilder {
        editTextPickerModel.type = CONSTANTS.PICKER_TYPE.RANGE
        editTextPickerModel.minvalue = minValue
        editTextPickerModel.maxvalue = maxValue
        editTextPickerModel.rangedefaultvalue = defaultValue
        return this
    }

    fun setMask(mask: String): EditTextPickerBuilder {
        editTextPickerModel.mask = mask
        return this
    }

    fun setRequired(required: Boolean): EditTextPickerBuilder {
        editTextPickerModel.required = required
        return this
    }

    fun setPattern(pattern: String): EditTextPickerBuilder {
        editTextPickerModel.pattern = pattern
        return this
    }

    @JvmOverloads
    fun setEqual(pattern: String, defaultValue: String = StringUtils.EMPTY): EditTextPickerBuilder {
        editTextPickerModel.type = CONSTANTS.PICKER_TYPE.EQUAL
        editTextPickerModel.pattern = pattern
        editTextPickerModel.defaultvalue = defaultValue
        return this
    }

    fun build(): EditTextModel {
        EditTextModel.editTextPickerModel = editTextPickerModel
        return EditTextModel
    }


}