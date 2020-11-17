package com.edittextpicker.aliazaz.edittextpicker

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.edittextpicker.aliazaz.EditTextPicker
import com.edittextpicker.aliazaz.repository.EditTextPickerItems
import kotlinx.android.synthetic.main.activity_main.*

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

//        val edut = EditTextPicker(this).builder(Values().minvalue)

        txtPicker = EditTextPicker(this, EditTextPickerItems().setRequired(true).create())
        llLayout.addView(txtPicker)

    }

    private fun validateComponents(): Boolean {
        /*if (!txtBoxRange.isRangeTextValidate()) return false
        if (!txtBoxRangeMaskPat.isRangeTextValidate()) return false
        if (!txtBoxDefault.isTextEqualToPattern()) return false
        if (!txtDate.isEmptyTextBox()) return false
        if (!txtPhone.isEmptyTextBox()) return false*/
        return txtPicker.isEmptyTextBox()
    }

    private fun clearFields() {
        txtBoxRange.text = null
        txtBoxRangeMaskPat.text = null
        txtBoxDefault.text = null
        txtDate.text = null
        txtPhone.text = null
    }
}