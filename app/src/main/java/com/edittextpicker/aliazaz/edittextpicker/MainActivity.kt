package com.edittextpicker.aliazaz.edittextpicker

import android.text.InputType
import android.widget.Toast
import com.edittextpicker.aliazaz.EditTextPicker
import com.edittextpicker.aliazaz.edittextpicker.databinding.ActivityMainBinding
import com.edittextpicker.aliazaz.repository.EditTextPickerBuilder

/*
* @author Ali Azaz Alam
* */
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private var txtPicker: EditTextPicker? = null
    override fun getLayout(): Int = R.layout.activity_main

    override fun init() {
        settingListeners()
    }

    private fun settingListeners() {
        binding.btnSubmit.setOnClickListener {
            if (validateComponents()) {
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.form_submitted),
                    Toast.LENGTH_SHORT
                )
                    .show()
                clearFields()
            }
        }

        /*
        * Setting date mask to txtDate
        * */
        binding.txtDate.setMask("##-##-####").setRequired(false)

        binding.txtPhone.setMaskText("3453323212")

        // Create EditTextPicker programmatically
        txtPicker = EditTextPicker(this, EditTextPickerBuilder().apply {
            setRequired(true)
            setRangeValues(0.5f, 40.0f)
            setMask("##.##")
            setPattern("^(\\d{2,2}\\.\\d{2,2})$")
        }.build()).apply {
            hint = "##.##"
            inputType = InputType.TYPE_CLASS_NUMBER
        }
        binding.llLayout.addView(txtPicker)

    }

    private fun validateComponents(): Boolean {
        if (!binding.txtBoxRange.isRangeTextValidate()) return false
        else if (txtPicker?.isRangeTextValidate() == false) return false
        else if (!binding.txtBoxDefault.isTextEqualToPattern()) return false
        else if (!binding.txtDate.isEmptyTextBox()) return false
        return binding.txtPhone.isEmptyTextBox()
    }

    private fun clearFields() {
        binding.txtBoxRange.text = null
        txtPicker?.text?.clear()
        binding.txtBoxDefault.text = null
        binding.txtDate.text = null
        binding.txtPhone.text = null
    }
}