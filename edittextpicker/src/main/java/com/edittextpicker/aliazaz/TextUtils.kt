package com.edittextpicker.aliazaz

import android.text.InputFilter
import android.text.InputFilter.LengthFilter

internal object TextUtils {

    // call in afterTextChanged event
    @JvmStatic
    fun editTextLoopToNextChar(maskEdit: String, position: Int): StringBuilder {
        val finalResult = StringBuilder()
        loop@ for (i in position until maskEdit.length) {
            /*if (maskEdit[i] != '#') {
                finalResult.append(maskEdit[i])
            } else break*/

            when {
                maskEdit[i] != '#' -> finalResult.append(maskEdit[i])
                else -> break@loop
            }
        }
        return finalResult
    }

    // set length
    @JvmStatic
    fun setLengthEditText(maskText: String): Array<InputFilter> = arrayOf(LengthFilter(maskText.length))

}