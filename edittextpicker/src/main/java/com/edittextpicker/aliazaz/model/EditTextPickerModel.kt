package com.edittextpicker.aliazaz.model

/*
* @author Ali Azaz Alam
* */
internal data class EditTextPickerModel(
        var type: Int = 0,

        /*
        * For Type range: Required minvalue, maxvalue and its corresponding default value
        * */
        var minvalue: Float = -1f,
        var maxvalue: Float = -1f,
        var rangedefaultvalue: Float = -1f,

        /*
        * For Type equal: Required pattern and its corresponding default value
        * */
        var defaultvalue: String = "",
        var pattern: String? = null,

        /*
        * Mask and Required: Both are optional
        * */
        var mask: String? = null,
        var required: Boolean = true

)