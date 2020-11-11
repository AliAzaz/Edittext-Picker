package com.edittextpicker.aliazaz

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.edittextpicker.aliazaz.repository.BaseTextWatcher
import com.edittextpicker.aliazaz.utils.ValuesUtils
import com.edittextpicker.aliazaz.utils.clearError
import com.edittextpicker.aliazaz.utils.setMaskEditTextLength
import timber.log.Timber
import kotlin.math.roundToLong

/*
* Update by: Ali Azaz Alam
* */
class EditTextPicker : AppCompatEditText {

    var type: Int = 0

    /*
    * For Type range: Required minvalue, maxvalue and its corresponding default value
    * */
    var minvalue: Float = -1f
    var maxvalue: Float = -1f
    var rangedefaultvalue: Float = -1f

    /*
    * For Type equal: Required pattern and its corresponding default value
    * */
    var defaultvalue: String? = null
    var pattern: String? = null

    /*
    * Mask and Required: Both are optional
    * */
    var mask: String? = null
    var required: Boolean = true

    /*
    * Initializing TextWatcher
    * */
    private var baseTextWatcher: BaseTextWatcher? = null


    init {
        /*
        * Initializing timber log only for debug state
        * */
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }


    /*
    * Initialize class with only context pass as an argument
    * */
    constructor(context: Context) : super(context)


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
                    required = getBoolean(R.styleable.EditTextPicker_required, true)


                    /*
                    * 1. Setting pattern attribute value
                    * 2. Optional
                    * */
                    pattern = getString(R.styleable.EditTextPicker_pattern)


                    /*
                    * 1. Setting mask attribute value
                    * 2. Initializing TextWatcher -> if mask value is not null else remove initialized TextWatcher
                    * */
                    mask = getString(R.styleable.EditTextPicker_mask)
                    if (mask.isNullOrEmpty()) removeTextChangedListener(baseTextWatcher)
                    else {
                        baseTextWatcher = BaseTextWatcher(mask)
                        addTextChangedListener(baseTextWatcher)
                        if (mask.toString().trim { it <= ' ' }.isNotEmpty()) {
                            setMaskEditTextLength(this@EditTextPicker, mask!!)
                        }
                    }


                    /*
                    * 1. Setting type attribute value whether it's range and equal
                    * 2. For range ->
                    *   i) Required min and max value (float)
                    *   ii) Define default value is Optional
                    * 3. For equal ->
                    *   i) Required pattern value
                    *   ii) Define default value is Optional
                    * */
                    type = getInteger(R.styleable.EditTextPicker_type, 0)
                    if (type == 1) {
                        minvalue = getFloat(R.styleable.EditTextPicker_minValue, -1f)
                        maxvalue = getFloat(R.styleable.EditTextPicker_maxValue, -1f)
                        rangedefaultvalue = getFloat(R.styleable.EditTextPicker_defaultValue, -1f)
                        if (minvalue == -1f) throw RuntimeException("Min value attribute not provided in xml")
                        if (maxvalue == -1f) throw RuntimeException("Max value attribute not provided in xml")
                    } else if (type == 2) {
                        pattern = getString(R.styleable.EditTextPicker_pattern)
                        defaultvalue = getString(R.styleable.EditTextPicker_defaultValue)
                        if (pattern == null) throw RuntimeException("Pattern value attribute not provided in xml")
                        if (defaultvalue == null) defaultvalue = ""
                    }
                }
            } catch (e: Exception) {
                Timber.e("TextPicker: %s", e.message)
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
        if (!required) return true
        if (super.getText().toString().isEmpty()) {
            Timber.i(this.context.resources.getResourceEntryName(super.getId())?.let { "$it:%s" }
                    ?: "%s", " Empty")
            super.setError(customError ?: "Required")
            super.setFocusableInTouchMode(true)
            super.requestFocus()
            invalidate()
            return false
        }
        invalidate()
        return true
    }


    /*
    * Access this functionality by setting type = range
    * This validation identifying whether EditText fulfilling specified min and max range or defualtValue or not.
    * */
    @JvmOverloads
    fun isRangeTextValidate(customError: String? = null): Boolean {
        clearError(this)
        if (type == 2) return true
        if (!required) return true
        if (!isEmptyTextBox()) return false
        if (!checkingTextPattern(null)) return false
        if (super.getText().toString().toFloat() < minvalue || super.getText().toString().toFloat() > maxvalue) {
            if (rangedefaultvalue != -1f) {
                var dValue = super.getText().toString().toFloat()
                if (super.getText().toString().toFloat() == super.getText().toString().toFloat().roundToLong().toFloat())
                    dValue = super.getText().toString().split("\\.").toTypedArray()[0].toFloat()
                if (dValue == rangedefaultvalue) {
                    invalidate()
                    return true
                }
            }
            var minVal = minvalue.toString()
            var maxVal = maxvalue.toString()
            if (minvalue == minvalue.roundToLong().toFloat()) minVal = minVal.split(".").toTypedArray()[0]
            if (maxvalue == maxvalue.roundToLong().toFloat()) maxVal = maxVal.split(".").toTypedArray()[0]
            super.setError(customError ?: "The range is from $minVal to $maxVal ")
            super.setFocusableInTouchMode(true)
            super.requestFocus()
            Timber.i(this.context.resources.getResourceEntryName(super.getId())?.let { "$it:%s" }
                    ?: "%s", " The defined range is from $minVal to $maxVal ")
            invalidate()
            return false
        }
        invalidate()
        return true
    }


    /*
    * Access this functionality by setting type = equal
    * This validation identifying whether EditText passing defined regex pattern or equal to default value.
    * */
    @JvmOverloads
    fun isTextEqualToPattern(customError: String? = null): Boolean {
        clearError(this)
        if (!required) return true
        if (!isEmptyTextBox()) return false
        if (!checkingTextPattern(customError))
            return when {
                type == 2 && super.getText().toString() == defaultvalue.toString() -> {
                    clearError(this)
                    invalidate()
                    true
                }
                else -> false
            }
        return true
    }


    /*
    * Private function that's using to check EditText matches to pattern or not and return this flag
    * */
    private fun checkingTextPattern(customError: String?): Boolean {
        pattern?.let {
            return when {
                !super.getText().toString().matches(Regex(pattern!!)) -> {
                    super.setError(customError ?: "Not match with pattern")
                    super.setFocusableInTouchMode(true)
                    super.requestFocus()
                    Timber.i(this.context.resources.getResourceEntryName(super.getId())?.let { "$it:%s" }
                            ?: "%s", "  Not matching with defined regex pattern")
                    invalidate()
                    false
                }
                else -> {
                    clearError(this)
                    invalidate()
                    true
                }
            }
        } ?: return true
    }


    fun builder(values: ValuesUtils): Any {
        TODO("Not yet implemented")
    }
}