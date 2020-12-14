package com.edittextpicker.aliazaz.repository

import com.edittextpicker.aliazaz.model.EditTextModel
import com.edittextpicker.aliazaz.model.ValuesModel

/*
* @author Ali Azaz Alam
* */
class EditTextPickerItems {

    private var valuesModel: ValuesModel = ValuesModel()

    @JvmOverloads
    fun setRangeValues(minValue: Float, maxValue: Float, defaultValue: Float = -1f): EditTextPickerItems {
        valuesModel.type = 1
        valuesModel.minvalue = minValue
        valuesModel.maxvalue = maxValue
        valuesModel.rangedefaultvalue = defaultValue
        return this
    }

    fun setMask(mask: String): EditTextPickerItems {
        valuesModel.mask = mask
        return this
    }

    fun setRequired(required: Boolean): EditTextPickerItems {
        valuesModel.required = required
        return this
    }

    fun setPattern(pattern: String): EditTextPickerItems {
        valuesModel.pattern = pattern
        return this
    }

    @JvmOverloads
    fun setEqual(pattern: String, defaultValue: String = ""): EditTextPickerItems {
        valuesModel.type = 2
        valuesModel.pattern = pattern
        valuesModel.defaultvalue = defaultValue
        return this
    }

    fun create(): EditTextModel {
        EditTextModel.valuesModel = valuesModel
        return EditTextModel
    }


}