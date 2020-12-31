package com.edittextpicker.aliazaz

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.edittextpicker.aliazaz.model.EditTextModel
import com.edittextpicker.aliazaz.model.EditTextPickerModel
import com.edittextpicker.aliazaz.repository.EditTextPickerWatcher
import com.edittextpicker.aliazaz.utils.EditTextViews
import com.edittextpicker.aliazaz.utils.clearError
import com.edittextpicker.aliazaz.utils.setMaskEditTextLength
import kotlin.math.roundToLong

/*
* @author Ali Azaz Alam
* */
class EditTextPicker : AppCompatEditText, EditTextViews {

    /*
    * Initializing TextWatcher
    * */
    private var editTextPickerWatcher: EditTextPickerWatcher? = null

    /*
    * Declaring editTextPickerModel data class, used to store EditTextPicker properties values
    * */
    private lateinit var editTextPickerModel: EditTextPickerModel


    /*
    * Initialize class with {@param context}, and {@param EditTextModel} pass as an argument. Used while creating EditTextPicker programmatically
    * */
    constructor(context: Context, editTextPickerModel: EditTextModel) : super(context) {
        this.editTextPickerModel = editTextPickerModel.editTextPickerModel!!
        maskingListener()
    }


    /*
    * Initialize class with {@param context}, {@param attributeSet} and {@param EditTextModel} pass as an argument. Used while creating EditTextPicker programmatically
    * */
    constructor(context: Context, defaultStyle: Int, editTextPickerModel: EditTextModel) : super(context, null, defaultStyle) {
        this.editTextPickerModel = editTextPickerModel.editTextPickerModel!!
        maskingListener()
    }


    /*
    * Initialize class with {@param context}, and {@param attributeSet} pass as an argument
    * */
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs)
    }


    /*
    * Initialize class with {@param context}, {@param attributeSet} and {@param defaultStyle} pass as an argument
    * */
    constructor(context: Context, attrs: AttributeSet?, defaultStyle: Int) : super(context, attrs, defaultStyle) {
        initialize(context, attrs)
    }


    /*
    * Get attribute setter properties from xml and set it to the specific variables
    * */
    private fun initialize(context: Context, attrs: AttributeSet?) {
        editTextPickerModel = EditTextPickerModel()
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
                    editTextPickerModel.required = getBoolean(R.styleable.EditTextPicker_required, true)


                    /*
                    * 1. Setting pattern attribute value
                    * 2. Optional
                    * */
                    editTextPickerModel.pattern = getString(R.styleable.EditTextPicker_pattern)


                    /*
                    * 1. Setting mask attribute value
                    * 2. Initializing TextWatcher -> if mask value is not null else remove initialized TextWatcher
                    * */
                    editTextPickerModel.mask = getString(R.styleable.EditTextPicker_mask)
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
                    editTextPickerModel.type = getInteger(R.styleable.EditTextPicker_type, 0)
                    if (editTextPickerModel.type == 1) {
                        editTextPickerModel.minvalue = getFloat(R.styleable.EditTextPicker_minValue, -1f)
                        editTextPickerModel.maxvalue = getFloat(R.styleable.EditTextPicker_maxValue, -1f)
                        editTextPickerModel.rangedefaultvalue = getFloat(R.styleable.EditTextPicker_defaultValue, -1f)
                        if (editTextPickerModel.minvalue == -1f) throw RuntimeException("Min value attribute not provided in xml")
                        if (editTextPickerModel.maxvalue == -1f) throw RuntimeException("Max value attribute not provided in xml")
                    } else if (editTextPickerModel.type == 2) {
                        editTextPickerModel.pattern = getString(R.styleable.EditTextPicker_pattern)
                        editTextPickerModel.defaultvalue = getString(R.styleable.EditTextPicker_defaultValue)
                                ?: ""
                        if (editTextPickerModel.pattern == null) throw RuntimeException("Pattern value attribute not provided in xml")
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
    * {@param customError} set custom error to EditText
    * */
    @JvmOverloads
    fun isEmptyTextBox(customError: String? = null): Boolean {
        clearError(this)
        if (!editTextPickerModel.required) return true
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
    * {@param customError} set custom error to EditText
    * */
    @JvmOverloads
    fun isRangeTextValidate(customError: String? = null): Boolean {
        clearError(this)
        if (editTextPickerModel.type == 2) return true
        if (!editTextPickerModel.required) return true
        if (!isEmptyTextBox()) return false
        if (!checkingTextPattern(null)) return false
        if (super.getText().toString().toFloat() < editTextPickerModel.minvalue || super.getText().toString().toFloat() > editTextPickerModel.maxvalue) {
            if (editTextPickerModel.rangedefaultvalue != -1f) {
                var dValue = super.getText().toString().toFloat()
                if (super.getText().toString().toFloat() == super.getText().toString().toFloat().roundToLong().toFloat())
                    dValue = super.getText().toString().split("\\.").toTypedArray()[0].toFloat()
                if (dValue == editTextPickerModel.rangedefaultvalue) {
                    return true
                }
            }
            var minVal = editTextPickerModel.minvalue.toString()
            var maxVal = editTextPickerModel.maxvalue.toString()
            if (editTextPickerModel.minvalue == editTextPickerModel.minvalue.roundToLong().toFloat()) minVal = minVal.split(".").toTypedArray()[0]
            if (editTextPickerModel.maxvalue == editTextPickerModel.maxvalue.roundToLong().toFloat()) maxVal = maxVal.split(".").toTypedArray()[0]
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
    * {@param customError} set custom error to EditText
    * */
    @JvmOverloads
    fun isTextEqualToPattern(customError: String? = null): Boolean {
        clearError(this)
        if (!editTextPickerModel.required) return true
        if (!isEmptyTextBox()) return false
        if (!checkingTextPattern(customError))
            return when {
                editTextPickerModel.type == 2 && super.getText().toString() == editTextPickerModel.defaultvalue -> {
                    clearError(this)
                    true
                }
                else -> false
            }
        return true
    }


    /*
    * Check EditText matches to pattern or not and return this flag
    * {@param customError} set custom error to EditText
    * */
    private fun checkingTextPattern(customError: String?): Boolean {
        editTextPickerModel.pattern?.let {
            return when {
                !super.getText().toString().matches(Regex(it)) -> {
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
        if (editTextPickerModel.mask.isNullOrEmpty()) removeTextChangedListener(editTextPickerWatcher)
        else {
            editTextPickerWatcher = EditTextPickerWatcher(editTextPickerModel.mask)
            addTextChangedListener(editTextPickerWatcher)
            if (editTextPickerModel.mask.toString().trim { it <= ' ' }.isNotEmpty()) {
                setMaskEditTextLength(this@EditTextPicker, editTextPickerModel.mask!!)
            }
        }
    }


    /*
    * {@param mask} Set mask text to EditText
    * call [maskingListener] for performing masking
    * */
    override fun setMask(mask: String): EditTextPicker {
        editTextPickerModel.mask = mask
        maskingListener()
        return this
    }


    /*
    * {@param required} Set required text to EditText
    * */
    override fun setRequired(required: Boolean): EditTextPicker {
        editTextPickerModel.required = required
        return this
    }


    /*
    * {@param pattern} Set pattern to EditText
    * */
    override fun setPattern(pattern: String): EditTextPicker {
        editTextPickerModel.pattern = pattern
        return this
    }


    /*
    * {@param minValue} Set minValue to EditText
    * */
    override fun setMinValue(minValue: Float): EditTextPicker {
        editTextPickerModel.minvalue = minValue
        return this
    }


    /*
    * {@param maxValue} Set maxValue to EditText
    * */
    override fun setMaxValue(maxValue: Float): EditTextPicker {
        editTextPickerModel.maxvalue = maxValue
        return this
    }


    /*
    * {@param defaultValue} Set rangedefaultvalue to EditText
    * */
    override fun setRangeDefaultValue(defaultValue: Float): EditTextPicker {
        editTextPickerModel.rangedefaultvalue = defaultValue
        return this
    }


    /*
    * {@param defaultValue} Set equalDefaultValue to EditText
    * */
    override fun setEqualDefaultValue(defaultValue: String): EditTextPicker {
        editTextPickerModel.defaultvalue = defaultValue
        return this
    }

}