package com.edittextpicker.aliazaz.edittextpicker

import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.edittextpicker.aliazaz.EditTextPicker
import com.edittextpicker.aliazaz.repository.EditTextPickerItems
import kotlinx.android.synthetic.main.activity_main.*

/*
* @author Ali Azaz Alam
* */
class MainActivity : AppCompatActivity() {

    lateinit var txtPicker: EditTextPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        settingListeners()
    }

    private fun settingListeners() {
        btnSubmit.setOnClickListener {
            if (validateComponents()) {
                Toast.makeText(this@MainActivity, getString(R.string.form_submitted), Toast.LENGTH_SHORT)
                    .show()
                clearFields()
            }
        }

        /*
        * Setting date mask to txtDate
        * */
        txtDate.setMask("##-##-####").setRequired(false)

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
        llLayout.addView(txtPicker)

    }

    private fun validateComponents(): Boolean {
        if (!txtBoxRange.isRangeTextValidate()) return false
        else if (!txtPicker.isRangeTextValidate()) return false
        else if (!txtBoxDefault.isTextEqualToPattern()) return false
        else if (!txtDate.isEmptyTextBox()) return false
        return txtPhone.isEmptyTextBox()
    }

    private fun clearFields() {
        txtBoxRange.text = null
        txtPicker.text = null
        txtBoxDefault.text = null
        txtDate.text = null
        txtPhone.text = null
    }
}