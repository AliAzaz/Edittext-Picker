package com.edittextpicker.aliazaz.edittextpicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.edittextpicker.aliazaz.textpicker.TextPicker;

public class MainActivity extends AppCompatActivity {

    Button btnSubmit;
    EditText txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializingComponents();
        settingListeners();

    }

    private void initializingComponents() {
        btnSubmit = findViewById(R.id.btnSubmit);
        txt = findViewById(R.id.txtMask);
    }

    private void settingListeners() {

        ((TextPicker) txt).isEmptyTextBox();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}
