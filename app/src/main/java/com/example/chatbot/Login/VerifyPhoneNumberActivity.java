package com.example.chatbot.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatbot.MainActivity;
import com.example.chatbot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneNumberActivity extends AppCompatActivity {

    String phoneNumber;
    EditText et1, et2, et3, et4, et5, et6;
    private String codeSent;
    public Button verifyButton;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    TextView instruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);

        instruction = findViewById(R.id.instruction);

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

        verifyButton = findViewById(R.id.verifyButtonId);

        phoneNumber = getIntent().getStringExtra("phonenumber");

        sendVerificationCode(phoneNumber);

        auth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressbar);

    }

    public void otpEntered(View view) {
        String code = "";
        if (et1.getText().toString().length() == 1 && et2.getText().toString().length() == 1
                && et3.getText().toString().length() == 1 && et4.getText().toString().length() == 1
                && et5.getText().toString().length() == 1 && et6.getText().toString().length() == 1) {

            code = et1.getText().toString();
            code = code + et2.getText().toString();
            code = code + et3.getText().toString();
            code = code + et4.getText().toString();
            code = code + et5.getText().toString();
            code = code + et6.getText().toString();
            progressBar.setVisibility(View.VISIBLE);
            verifyCode(code);

        } else {
            Toast.makeText(this, "Please enter a valid OTP", Toast.LENGTH_SHORT).show();
        }
    }


    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent,code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(VerifyPhoneNumberActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            Toast.makeText(VerifyPhoneNumberActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void sendVerificationCode(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber,60, TimeUnit.SECONDS, this,mCallBack);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            instruction.setVisibility(View.VISIBLE);
            codeSent = s;
        }



        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                String code1, code2, code3, code4, code5, code6;
                code1 = code.substring(0,1);
                code2 = code.substring(1,2);
                code3 = code.substring(2,3);
                code4 = code.substring(3,4);
                code5 = code.substring(4,5);
                code6 = code.substring(5);
                et1.setText(code1);
                et2.setText(code2);
                et3.setText(code3);
                et4.setText(code4);
                et5.setText(code5);
                et6.setText(code6);
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }

            Intent intent = new Intent(VerifyPhoneNumberActivity.this, MainActivity.class);
            startActivity(intent);

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyPhoneNumberActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(VerifyPhoneNumberActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
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
