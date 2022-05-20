package com.jomqr.app;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText txtEmail, txtPassword;
    private Button  btnRegister, btnBack;
    private FirebaseAuth auth;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);


        btnRegister = findViewById(R.id.btnRegister);
        btnBack = findViewById(R.id.btnBack);



        
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                if(!txtEmail.getText().toString().trim().isEmpty() && !txtPassword.getText().toString().trim().isEmpty()) {
                    auth.createUserWithEmailAndPassword(txtEmail.getText().toString().trim(),txtPassword.getText().toString().trim()).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                ref.child("Users").child(task.getResult().getUser().getUid()).child("balance").setValue("0");
                                Toast.makeText(RegisterActivity.this, "Successful registration", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this,additionalDetails.class);
                                intent.putExtra("uid",task.getResult().getUser().getUid());
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(RegisterActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
