package com.edittextpicker.aliazaz.utils

import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import com.edittextpicker.aliazaz.EditTextPicker

/*
* Update by: Ali Azaz Alam
* */

// call in afterTextChanged event
internal fun editTextLoopToNextChar(maskEdit: String, position: Int): StringBuilder {
    val finalResult = StringBuilder()
    loop@ for (i in position until maskEdit.length) {
        when {
            maskEdit[i] != '#' -> finalResult.append(maskEdit[i])
            else -> break@loop
        }
    }
    return finalResult
}

// set length
internal fun setLengthEditText(maskText: String): Array<InputFilter> = arrayOf(LengthFilter(maskText.length))


// Clearing error on fields
internal fun clearError(editTextPicker: EditTextPicker) {
    editTextPicker.error = null
}


/*
* Private function for setting mask EditText length
* Calculate length from mask length
* */
internal fun setMaskEditTextLength(editTextPicker: EditTextPicker, mask: String) {
    editTextPicker.filters = setLengthEditText(mask)
}

