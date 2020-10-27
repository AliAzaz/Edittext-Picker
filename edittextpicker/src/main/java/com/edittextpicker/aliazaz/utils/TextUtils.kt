package com.edittextpicker.aliazaz.utils

import android.text.InputFilter
import android.text.InputFilter.LengthFilter

internal object TextUtils {

    // call in afterTextChanged event
    fun editTextLoopToNextChar(maskEdit: String, position: Int): StringBuilder {
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
    fun setLengthEditText(maskText: String): Array<InputFilter> = arrayOf(LengthFilter(maskText.length))

}