package com.edittextpicker.aliazaz

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.edittextpicker.aliazaz.utils.TextUtils.editTextLoopToNextChar
import com.edittextpicker.aliazaz.utils.TextUtils.setLengthEditText
import timber.log.Timber
import kotlin.math.roundToLong

class EditTextPicker(context: Context, attrs: AttributeSet?) : AppCompatEditText(context, attrs), TextWatcher {
    var minvalue: Float = -1f
    var maxvalue: Float = -1f
    var rangedefaultvalue: Float = -1f
    var defaultvalue: String? = null
    var mask: String? = null
    var pattern: String? = null
    var type: Int = 0
    var required: Boolean = true
    private var maskCheckFlag = true

    companion object {
        var TAG = EditTextPicker::class.java.name
    }

    init {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        super.addTextChangedListener(this)
        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                    attrs,
                    R.styleable.EditTextPicker,
                    0, 0
            )
            try {
                //required flag
                required = a.getBoolean(R.styleable.EditTextPicker_required, true)

                //Pattern
                pattern = a.getString(R.styleable.EditTextPicker_pattern)

                // For mask
                mask = a.getString(R.styleable.EditTextPicker_mask)
                if (mask == null) super@EditTextPicker.removeTextChangedListener(this)
                else {
                    if (mask!!.trim { it <= ' ' }.isNotEmpty()) {
                        maskingEditText(mask!!)
                    }
                }

                //For type -> range and equal
                type = a.getInteger(R.styleable.EditTextPicker_type, 0)
                if (type == 1) {
                    minvalue = a.getFloat(R.styleable.EditTextPicker_minValue, -1f)
                    maxvalue = a.getFloat(R.styleable.EditTextPicker_maxValue, -1f)
                    rangedefaultvalue = a.getFloat(R.styleable.EditTextPicker_defaultValue, -1f)
                    if (minvalue == -1f) throw RuntimeException("Min value not provided")
                    if (maxvalue == -1f) throw RuntimeException("Max value not provided")
                } else if (type == 2) {
                    defaultvalue = a.getString(R.styleable.EditTextPicker_defaultValue)
                    if (defaultvalue == null) throw RuntimeException("Default value not provided")
                }
            } catch (e: Exception) {
                Timber.e("TextPicker: %s", e.message)
                throw e
            } finally {
                a.recycle()
            }
        }
    }


    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        mask?.let { maskCheckFlag = i2 != 0 }
    }

    override fun afterTextChanged(editable: Editable) {
        mask?.let {
            if (!maskCheckFlag) return
            val txt = editTextLoopToNextChar(mask!!, editable.length - 1)
            if (txt.isEmpty()) return
            //Input Filter work
            val filters = editable.filters //get filter
            editable.filters = arrayOf() //reset filter
            editable.insert(editable.length - 1, txt)
            editable.filters = filters //restore filter
        }
    }

    // call for checking empty textbox
    val isEmptyTextBox: Boolean
        get() {
            clearError()
            if (!required) return true
            if (super.getText().toString().isEmpty()) {
                Timber.i(this.context.resources.getResourceEntryName(super.getId())?.let { "$it:%s" }
                        ?: "%s", " Empty")
                super.setError("Required")
                super.setFocusableInTouchMode(true)
                super.requestFocus()
                invalidate()
                return false
            }
            clearError()
            invalidate()
            return true
        }

    // call for checking range textbox
    val isRangeTextValidate: Boolean
        get() {
            clearError()
            if (type != 1) return true
            if (!required) return true
            if (!isEmptyTextBox) return false
            if (!checkingTextPattern()) return false
            if (super.getText().toString().toFloat() < minvalue || super.getText().toString().toFloat() > maxvalue) {
                if (rangedefaultvalue != -1f) {
                    var dValue = super.getText().toString().toFloat()
                    if (super.getText().toString().toFloat() == super.getText().toString().toFloat().roundToLong().toFloat()) dValue = super.getText().toString().split("\\.").toTypedArray()[0].toFloat()
                    if (dValue == rangedefaultvalue) {
                        invalidate()
                        return true
                    }
                }
                var minVal = minvalue.toString()
                var maxVal = maxvalue.toString()
                if (minvalue == minvalue.roundToLong().toFloat()) minVal = minVal.split(".").toTypedArray()[0]
                if (maxvalue == maxvalue.roundToLong().toFloat()) maxVal = maxVal.split(".").toTypedArray()[0]
                super.setError("Range is $minVal to $maxVal ")
                super.setFocusableInTouchMode(true)
                super.requestFocus()
                Timber.i(this.context.resources.getResourceEntryName(super.getId())?.let { "$it:%s" }
                        ?: "%s", " The defined range is $minVal to $maxVal ")
                invalidate()
                return false
            }
            clearError()
            invalidate()
            return true
        }

    // call for checking default value in textbox
    val isTextEqualToPattern: Boolean
        get() {
            clearError()
            if (!required) return true
            if (!isEmptyTextBox) return false
            if (!checkingTextPattern())
                return when {
                    type == 2 && super.getText().toString() == defaultvalue.toString() -> {
                        clearError()
                        invalidate()
                        true
                    }
                    else -> false
                }
            return true
        }

    // Checking pattern
    private fun checkingTextPattern(): Boolean {
        pattern?.let {
            return when {
                !super.getText().toString().matches(Regex(pattern!!)) -> {
                    super.setError("Not match the pattern")
                    super.setFocusableInTouchMode(true)
                    super.requestFocus()
                    Timber.i(this.context.resources.getResourceEntryName(super.getId())?.let { "$it:%s" }
                            ?: "%s", "  Not match the pattern")
                    invalidate()
                    false
                }
                else -> {
                    clearError()
                    invalidate()
                    true
                }
            }
        } ?: return true
    }

    // For maskingEditText length setting
    private fun maskingEditText(mask: String) = super.setFilters(setLengthEditText(mask))

    // Clearing error on fields
    private fun clearError() = super.setError(null)
}