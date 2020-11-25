package com.edittextpicker.aliazaz

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.edittextpicker.aliazaz.model.ValuesModel
import com.edittextpicker.aliazaz.repository.EditTextPickerWatcher
import com.edittextpicker.aliazaz.utils.clearError
import com.edittextpicker.aliazaz.utils.setMaskEditTextLength
import kotlin.math.roundToLong

/*
* @author Ali Azaz Alam
* */
class EditTextPicker : AppCompatEditText {

    /*
    * Initializing TextWatcher
    * */
    private var editTextPickerWatcher: EditTextPickerWatcher? = null

    /*
    * Declaring ValuesModel data class, used to store EditTextPicker properties values
    * */
    private lateinit var valuesModel: ValuesModel


    /*
    * Initialize class with context and ValuesModel pass as an argument. Used while creating EditTextPicker programmatically
    * */
    constructor(context: Context, valuesModel: ValuesModel) : super(context) {
        this.valuesModel = valuesModel
    }


    /*
    * Initialize class with context, defaultStyle and ValuesModel pass as an argument. Used while creating EditTextPicker programmatically
    * */
    constructor(context: Context, defaultStyle: Int, valuesModel: ValuesModel) : super(context, null, defaultStyle) {
        this.valuesModel = valuesModel
    }


    /*
    * Initialize class with context and attributeSet pass as an argument
    * */
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs)
    }


    /*
    * Initialize class with context, attributeSet and defaultStyle pass as an argument
    * */
    constructor(context: Context, attrs: AttributeSet?, defaultStyle: Int) : super(context, attrs, defaultStyle) {
        initialize(context, attrs)
    }


    /*
    * Get attribute setter properties from xml and set it to the specific variables
    * */
    private fun initialize(context: Context, attrs: AttributeSet?) {
        valuesModel = ValuesModel()
        attrs?.let { item ->
            val a = context.obtainStyledAttributes(
                    item,
                    R.styleable.EditTextPicker
            )
            try {
                a.run {
                    /*
                    * 1. Setting required attribute value
                    * 2. Default value is 'true'
                    * 3. Optional
                    * */
                    valuesModel.required = getBoolean(R.styleable.EditTextPicker_required, true)


                    /*
                    * 1. Setting pattern attribute value
                    * 2. Optional
                    * */
                    valuesModel.pattern = getString(R.styleable.EditTextPicker_pattern)


                    /*
                    * 1. Setting mask attribute value
                    * 2. Initializing TextWatcher -> if mask value is not null else remove initialized TextWatcher
                    * */
                    valuesModel.mask = getString(R.styleable.EditTextPicker_mask)
                    maskingListener()


                    /*
                    * 1. Setting type attribute value whether it's range and equal
                    * 2. For range ->
                    *   i) Required min and max value (float)
                    *   ii) Define default value is Optional
                    * 3. For equal ->
                    *   i) Required pattern value
                    *   ii) Define default value is Optional
                    * */
                    valuesModel.type = getInteger(R.styleable.EditTextPicker_type, 0)
                    if (valuesModel.type == 1) {
                        valuesModel.minvalue = getFloat(R.styleable.EditTextPicker_minValue, -1f)
                        valuesModel.maxvalue = getFloat(R.styleable.EditTextPicker_maxValue, -1f)
                        valuesModel.rangedefaultvalue = getFloat(R.styleable.EditTextPicker_defaultValue, -1f)
                        if (valuesModel.minvalue == -1f) throw RuntimeException("Min value attribute not provided in xml")
                        if (valuesModel.maxvalue == -1f) throw RuntimeException("Max value attribute not provided in xml")
                    } else if (valuesModel.type == 2) {
                        valuesModel.pattern = getString(R.styleable.EditTextPicker_pattern)
                        valuesModel.defaultvalue = getString(R.styleable.EditTextPicker_defaultValue)
                                ?: ""
                        if (valuesModel.pattern == null) throw RuntimeException("Pattern value attribute not provided in xml")
                    }
                }
            } catch (e: Exception) {
                throw e
            } finally {
                a.recycle()
            }
        }
    }


    /*
    * Validating EditText: Identifying it's empty or not
    * */
    @JvmOverloads
    fun isEmptyTextBox(customError: String? = null): Boolean {
        clearError(this)
        if (!valuesModel.required) return true
        if (super.getText().toString().isEmpty()) {
            super.setError(customError ?: "Required")
            super.setFocusableInTouchMode(true)
            super.requestFocus()
            return false
        }
        return true
    }


    /*
    * Access this functionality by setting type = range
    * This validation identifying whether EditText fulfilling specified min and max range or defualtValue or not.
    * */
    @JvmOverloads
    fun isRangeTextValidate(customError: String? = null): Boolean {
        clearError(this)
        if (valuesModel.type == 2) return true
        if (!valuesModel.required) return true
        if (!isEmptyTextBox()) return false
        if (!checkingTextPattern(null)) return false
        if (super.getText().toString().toFloat() < valuesModel.minvalue || super.getText().toString().toFloat() > valuesModel.maxvalue) {
            if (valuesModel.rangedefaultvalue != -1f) {
                var dValue = super.getText().toString().toFloat()
                if (super.getText().toString().toFloat() == super.getText().toString().toFloat().roundToLong().toFloat())
                    dValue = super.getText().toString().split("\\.").toTypedArray()[0].toFloat()
                if (dValue == valuesModel.rangedefaultvalue) {
                    return true
                }
            }
            var minVal = valuesModel.minvalue.toString()
            var maxVal = valuesModel.maxvalue.toString()
            if (valuesModel.minvalue == valuesModel.minvalue.roundToLong().toFloat()) minVal = minVal.split(".").toTypedArray()[0]
            if (valuesModel.maxvalue == valuesModel.maxvalue.roundToLong().toFloat()) maxVal = maxVal.split(".").toTypedArray()[0]
            super.setError(customError ?: "The range is from $minVal to $maxVal ")
            super.setFocusableInTouchMode(true)
            super.requestFocus()
            return false
        }
        return true
    }


    /*
    * Access this functionality by setting type = equal
    * This validation identifying whether EditText passing defined regex pattern or equal to default value.
    * */
    @JvmOverloads
    fun isTextEqualToPattern(customError: String? = null): Boolean {
        clearError(this)
        if (!valuesModel.required) return true
        if (!isEmptyTextBox()) return false
        if (!checkingTextPattern(customError))
            return when {
                valuesModel.type == 2 && super.getText().toString() == valuesModel.defaultvalue -> {
                    clearError(this)
                    true
                }
                else -> false
            }
        return true
    }


    /*
    * Check EditText matches to pattern or not and return this flag
    * */
    private fun checkingTextPattern(customError: String?): Boolean {
        valuesModel.pattern?.let {
            return when {
                !super.getText().toString().matches(Regex(valuesModel.pattern!!)) -> {
                    super.setError(customError ?: "Not match with pattern")
                    super.setFocusableInTouchMode(true)
                    super.requestFocus()
                    false
                }
                else -> {
                    clearError(this)
                    true
                }
            }
        } ?: return true
    }


    /*
    * [maskingListener] adding and removing TextChangedListener
    * */
    private fun maskingListener() {
        if (valuesModel.mask.isNullOrEmpty()) removeTextChangedListener(editTextPickerWatcher)
        else {
            editTextPickerWatcher = EditTextPickerWatcher(valuesModel.mask)
            addTextChangedListener(editTextPickerWatcher)
            if (valuesModel.mask.toString().trim { it <= ' ' }.isNotEmpty()) {
                setMaskEditTextLength(this@EditTextPicker, valuesModel.mask!!)
            }
        }
    }

}