package com.edittextpicker.aliazaz;

import android.text.InputFilter;

final class TextUtils {

    // call in afterTextChanged event
    public final static String editTextLoopToNextChar(String maskEdit, int position) {

        String finalResult = "";
        for (int i = position; i < maskEdit.length(); i++) {
            if (maskEdit.charAt(i) != '#') {
                finalResult += maskEdit.charAt(i);
            } else
                break;
        }

        return finalResult;
    }

    // set length
    public final static InputFilter[] setLengthEditText(String maskText) {
        return new InputFilter[]{new InputFilter.LengthFilter(maskText.length())};
    }

}
