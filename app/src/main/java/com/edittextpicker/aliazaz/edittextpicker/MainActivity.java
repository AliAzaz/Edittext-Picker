package com.edittextpicker.aliazaz.edittextpicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnSubmit;
    com.edittextpicker.aliazaz.EditTextPicker txtBoxRange, txtBoxRangeMaskPat, txtBoxDefault, txtDate, txtPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializingComponents();
        settingListeners();

    }

    private void initializingComponents() {
        btnSubmit = findViewById(R.id.btnSubmit);
        txtBoxRange = findViewById(R.id.txtBoxRange);
        txtBoxRangeMaskPat = findViewById(R.id.txtBoxRangeMaskPat);
        txtBoxDefault = findViewById(R.id.txtBoxDefault);
        txtDate = findViewById(R.id.txtDate);
        txtPhone = findViewById(R.id.txtPhone);
    }

    private void settingListeners() {

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validateComponents()) {
                    Toast.makeText(MainActivity.this, "Successfully submitted!!", Toast.LENGTH_SHORT).show();
                    clearFields();
                }

            }
        });

    }

    private boolean validateComponents() {

        if (!txtBoxRange.isRangeTextValidate())
            return false;

        if (!txtBoxRangeMaskPat.isRangeTextValidate())
            return false;

        if (!txtBoxDefault.isTextEqualToPattern())
            return false;

        if (!txtDate.isEmptyTextBox())
            return false;

        return txtPhone.isEmptyTextBox();
    }

    private void clearFields() {

        txtBoxRange.setText(null);
        txtBoxRangeMaskPat.setText(null);
        txtBoxDefault.setText(null);
        txtDate.setText(null);
        txtPhone.setText(null);

    }
}
