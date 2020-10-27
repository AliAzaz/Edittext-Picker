package com.edittextpicker.aliazaz.edittextpicker

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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
    }

    private fun validateComponents(): Boolean {
        if (!txtBoxRange.isRangeTextValidate) return false
        if (!txtBoxRangeMaskPat.isRangeTextValidate) return false
        if (!txtBoxDefault.isTextEqualToPattern) return false
        return if (!txtDate.isEmptyTextBox) false else txtPhone.isEmptyTextBox
    }

    private fun clearFields() {
        txtBoxRange.text = null
        txtBoxRangeMaskPat.text = null
        txtBoxDefault.text = null
        txtDate.text = null
        txtPhone.text = null
    }
}