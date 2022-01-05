package com.edittextpicker.aliazaz.edittextpicker

import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.edittextpicker.aliazaz.EditTextPicker
import com.edittextpicker.aliazaz.edittextpicker.databinding.ActivityMainBinding
import com.edittextpicker.aliazaz.repository.EditTextPickerItems

/*
* @author Ali Azaz Alam
* */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var txtPicker: EditTextPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

        // Create EditTextPicker programmatically
        txtPicker = EditTextPicker(this, EditTextPickerItems().apply {
            setRequired(true)
            setRangeValues(0.5f, 40.0f)
            setMask("##.##")
            setPattern("^(\\d{2,2}\\.\\d{2,2})$")
        }.create()).apply {
            hint = "##.##"
            inputType = InputType.TYPE_CLASS_NUMBER
        }
        binding.llLayout.addView(txtPicker)

    }

    private fun validateComponents(): Boolean {
        if (!binding.txtBoxRange.isRangeTextValidate()) return false
        else if (!txtPicker.isRangeTextValidate()) return false
        else if (!binding.txtBoxDefault.isTextEqualToPattern()) return false
        else if (!binding.txtDate.isEmptyTextBox()) return false
        return binding.txtPhone.isEmptyTextBox()
    }

    private fun clearFields() {
        binding.txtBoxRange.text = null
        txtPicker.text = null
        binding.txtBoxDefault.text = null
        binding.txtDate.text = null
        binding.txtPhone.text = null
    }
}