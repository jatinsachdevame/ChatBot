package com.example.chatbot.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatbot.R;

public class VerifyPhoeNumberActivity extends AppCompatActivity {

    String phoneNumber;
    EditText et1, et2, et3, et4, et5, et6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phoe_number);

        phoneNumber = getIntent().getStringExtra("phonenumber");
        Toast.makeText(this, phoneNumber, Toast.LENGTH_SHORT).show();
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
        et5 = findViewById(R.id.et5);
        et6 = findViewById(R.id.et6);

        et1.addTextChangedListener(new GenericTextWatcher(et1));
        et2.addTextChangedListener(new GenericTextWatcher(et2));
        et3.addTextChangedListener(new GenericTextWatcher(et3));
        et4.addTextChangedListener(new GenericTextWatcher(et4));
        et5.addTextChangedListener(new GenericTextWatcher(et5));
        et6.addTextChangedListener(new GenericTextWatcher(et6));
    }



    public class GenericTextWatcher implements TextWatcher {

        private View view;
        private GenericTextWatcher(View view)
        {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //DO something
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //Do something
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            switch (view.getId()) {
                case R.id.et1 :
                    if (text.length() == 1)
                        et2.requestFocus();
                    break;

                case R.id.et2 :
                    if (text.length() == 1)
                        et3.requestFocus();
                    else if(text.length()==0)
                        et1.requestFocus();
                    break;


                case R.id.et3 :
                    if (text.length() == 1)
                        et4.requestFocus();
                    else if(text.length()==0)
                        et2.requestFocus();
                    break;


                case R.id.et4 :
                    if (text.length() == 1)
                        et5.requestFocus();
                    else if(text.length()==0)
                        et3.requestFocus();
                    break;


                case R.id.et5 :
                    if (text.length() == 1)
                        et6.requestFocus();
                    else if(text.length()==0)
                        et4.requestFocus();
                    break;


                case R.id.et6 :
                    if(text.length()==0)
                    et5.requestFocus();
                    break;

            }
        }
    }

}
