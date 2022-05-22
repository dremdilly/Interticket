package com.interticket.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etUsername, etPassword;
    private Button btnLogin, btnForgotPassword, btnRegister;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null){
            startActivity(MainActivity.class);
            finish();
        }

        //set views
        etUsername = findViewById(R.id.txtUsername);
        etPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnForgotPassword = findViewById(R.id.btnForgotPassword);
        btnRegister = findViewById(R.id.btnRegister);

        //buttons action

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etUsername.getText().toString().trim().isEmpty() && !etPassword.getText().toString().trim().isEmpty()) {
                    auth.signInWithEmailAndPassword(etUsername.getText().toString().trim(), etPassword.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(MainActivity.class);
                                Toast.makeText(LoginActivity.this, "Welcome to JOM QR!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(LoginActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ForgotPasswordActivity.class);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(RegisterActivity.class);
            }
        });
    }

    private void startActivity(Class activityClass){
        Intent intent = new Intent(this,activityClass);
        startActivity(intent);
    }
}
