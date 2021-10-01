package com.edittextpicker.aliazaz.repository

import com.edittextpicker.aliazaz.model.EditTextModel
import com.edittextpicker.aliazaz.model.EditTextPickerModel
import org.apache.commons.lang3.StringUtils

/*
* @author Ali Azaz Alam
* */
class EditTextPickerItems {

//    private var editTextPickerModel: EditTextPickerModel = EditTextPickerModel()

    private val editTextPickerModel:EditTextPickerModel by lazy {
        EditTextPickerModel()
    }

    @JvmOverloads
    fun setRangeValues(minValue: Float, maxValue: Float, defaultValue: Float = -1f): EditTextPickerItems {
        editTextPickerModel.type = 1
        editTextPickerModel.minvalue = minValue
        editTextPickerModel.maxvalue = maxValue
        editTextPickerModel.rangedefaultvalue = defaultValue
        return this
    }

    fun setMask(mask: String): EditTextPickerItems {
        editTextPickerModel.mask = mask
        return this
    }

    fun setRequired(required: Boolean): EditTextPickerItems {
        editTextPickerModel.required = required
        return this
    }

    fun setPattern(pattern: String): EditTextPickerItems {
        editTextPickerModel.pattern = pattern
        return this
    }

    @JvmOverloads
    fun setEqual(pattern: String, defaultValue: String = StringUtils.EMPTY): EditTextPickerItems {
        editTextPickerModel.type = 2
        editTextPickerModel.pattern = pattern
        editTextPickerModel.defaultvalue = defaultValue
        return this
    }

    fun create(): EditTextModel {
        EditTextModel.editTextPickerModel = editTextPickerModel
        return EditTextModel
    }


}