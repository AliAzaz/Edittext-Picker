package com.edittextpicker.aliazaz.utils

import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import com.edittextpicker.aliazaz.EditTextPicker

/*
* @author Ali Azaz Alam
* */


/*
* Masking procedure algorithm to create editTextPicker masked
* */
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


/*
* Set maxLength of editTextPicker.
* {@param maskText} get maskText length and set as a maxLength of editTextPicker
* */
internal fun setLengthEditText(maskText: String): Array<InputFilter> = arrayOf(LengthFilter(maskText.length))


/*
* Clear error on editTextPicker
* {@param editTextPicker} used as ref. argument to setError null
* */
internal fun clearError(editTextPicker: EditTextPicker) {
    editTextPicker.error = null
//    editTextPicker.invalidate()
}


/*
* Set filters on editTextPicker
* {@param editTextPicker} as ref. and {@param mask} pass in [setLengthEditText]
* */
internal fun setMaskEditTextLength(editTextPicker: EditTextPicker, mask: String) {
    editTextPicker.filters = setLengthEditText(mask)
}

