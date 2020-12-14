package com.edittextpicker.aliazaz.repository

import android.text.Editable
import android.text.TextWatcher
import com.edittextpicker.aliazaz.utils.editTextLoopToNextChar

/*
* @author Ali Azaz Alam
* */

/*
* [EditTextPickerWatcher], implementing editTextPicker TextWatcher
* */
internal class EditTextPickerWatcher(private val mask: String?) : TextWatcher {

    private var maskCheckFlag = true

    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        mask?.let { maskCheckFlag = i2 != 0 }
    }

    override fun afterTextChanged(editable: Editable) {
        mask?.let {
            if (!maskCheckFlag) return
            val txt = editTextLoopToNextChar(mask, editable.length - 1)
            if (txt.isEmpty()) return
            //Input Filter work
            val filters = editable.filters //get filter
            editable.filters = arrayOf() //reset filter
            editable.insert(editable.length - 1, txt)
            editable.filters = filters //restore filter
        }
    }
}