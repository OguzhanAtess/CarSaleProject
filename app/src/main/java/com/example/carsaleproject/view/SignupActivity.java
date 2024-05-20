package com.example.carsaleproject.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carsaleproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    EditText userconfirmpass,usermail,userpassword;
    Button btn_login,btn_signup;
    FirebaseAuth auth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        userconfirmpass = findViewById(R.id.txt_confirm_pass);
        usermail = findViewById(R.id.txt_usermail);
        userpassword = findViewById(R.id.txt_userpassword);
        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);
        auth = FirebaseAuth.getInstance();

    }

    public void signupClicked(View view){
        String mail = usermail.getText().toString();
        String password = userpassword.getText().toString();
        String confirmpass = userconfirmpass.getText().toString();
        if(TextUtils.isEmpty(mail)){
            usermail.setError(getString(R.string.email_empty));
            usermail.requestFocus();
        } else if(TextUtils.isEmpty(password)){
            userpassword.setError(getString(R.string.password_empty));
            userpassword.requestFocus();
        } else if(TextUtils.isEmpty(confirmpass)){
            userconfirmpass.setError(getString(R.string.password_empty));
            userconfirmpass.requestFocus();
        } else if(!password.equals(confirmpass)) {
            userconfirmpass.setError(getString(R.string.pass_not_confirm));
            userconfirmpass.requestFocus();
        } else {
            auth.createUserWithEmailAndPassword(mail,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent = new Intent(SignupActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignupActivity.this, (R.string.signup_fail), Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    public void loginClicked(View view){
        Intent intent = new Intent(SignupActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}