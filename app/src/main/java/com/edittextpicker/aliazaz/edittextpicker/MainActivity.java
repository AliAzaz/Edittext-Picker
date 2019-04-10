package com.edittextpicker.aliazaz.edittextpicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edittextpicker.aliazaz.TextPicker;

public class MainActivity extends AppCompatActivity {

    Button btnSubmit;
    EditText txtBoxRange, txtBoxDefault, txtMask, textPhone;

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
        txtBoxDefault = findViewById(R.id.txtBoxDefault);
        txtMask = findViewById(R.id.txtMask);
        textPhone = findViewById(R.id.textPhone);
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

        if (!((TextPicker) txtBoxRange).isEmptyTextBox())
            return false;

        if (!((TextPicker) txtBoxRange).isRangeTextValidate())
            return false;

        return ((TextPicker) txtBoxDefault).isTextEqual();
    }

    private void clearFields() {

        txtBoxRange.setText(null);
        txtBoxDefault.setText(null);
        txtMask.setText(null);
        textPhone.setText(null);

    }
}
