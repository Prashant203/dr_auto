package com.example.dr_auto;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class GenericTextWatcher implements TextWatcher {
    private final EditText[] editText;
    private final View view;

    public GenericTextWatcher(View view, EditText[] editText) {
        this.editText = editText;
        this.view = view;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void afterTextChanged(Editable editable) {
        String text = editable.toString();
        switch (view.getId()) {

            case R.id.etDigit1:
                if (text.length() == 1) {
                    editText[1].requestFocus();
                    editText[1].setSelection(editText[1].length());
                }
                break;
            case R.id.etDigit2:

                if (text.length() == 1) {
                    editText[2].requestFocus();
                    editText[2].setSelection(editText[2].length());
                } else if (text.length() == 0) {
                    editText[0].requestFocus();
                    editText[0].setSelection(editText[0].length());
                }
                break;
            case R.id.etDigit3:
                if (text.length() == 1) {
                    editText[3].requestFocus();
                    editText[3].setSelection(editText[3].length());
                } else if (text.length() == 0) {
                    editText[1].requestFocus();
                    editText[1].setSelection(editText[1].length());
                }
                break;
            case R.id.etDigit4:
                if (text.length() == 1) {
                    editText[4].requestFocus();
                    editText[4].setSelection(editText[4].length());
                } else if (text.length() == 0) {
                    editText[2].requestFocus();
                    editText[2].setSelection(editText[2].length());
                }
                break;
            case R.id.etDigit5:
                if (text.length() == 1) {
                    editText[5].requestFocus();
                    editText[5].setSelection(editText[5].length());
                } else if (text.length() == 0) {
                    editText[3].requestFocus();
                    editText[3].setSelection(editText[3].length());
                }
                break;
            case R.id.etDigit6:
                if (text.length() == 0) {
                    editText[4].requestFocus();
                    editText[4].setSelection(editText[4].length());
                }
                break;
        }
    }

}