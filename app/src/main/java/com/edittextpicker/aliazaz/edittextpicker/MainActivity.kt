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
                Toast.makeText(this@MainActivity, "Successfully submitted", Toast.LENGTH_SHORT).show()
                clearFields()
            }
        }

        /*
        * Setting date mask to txtDate
        * */
        txtDate.setMask("##-##-####").setRequired(false)

        // Create Edittextpicker programatically
        txtPicker = EditTextPicker(this, EditTextPickerItems().setRequired(true).setRangeValues(2.0f, 33.5f).setMask("##.##").setPattern("^(\\d{2,2}\\.\\d{2,2})$").create())
        txtPicker.hint = "##.##"
        txtPicker.inputType = InputType.TYPE_CLASS_NUMBER
        llLayout.addView(txtPicker)

    }

    private fun validateComponents(): Boolean {
        if (!txtBoxRange.isRangeTextValidate()) return false
        else if (!txtBoxRangeMaskPat.isRangeTextValidate()) return false
        else if (!txtBoxDefault.isTextEqualToPattern()) return false
        else if (!txtDate.isEmptyTextBox()) return false
        else if (!txtPhone.isEmptyTextBox()) return false
        return txtPicker.isRangeTextValidate()
    }

    private fun clearFields() {
        txtBoxRange.text = null
        txtBoxRangeMaskPat.text = null
        txtBoxDefault.text = null
        txtDate.text = null
        txtPhone.text = null
        txtPicker.text = null
    }
}