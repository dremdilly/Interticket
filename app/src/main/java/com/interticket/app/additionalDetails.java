package com.interticket.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class additionalDetails extends AppCompatActivity {


    private TextInputEditText txtFirstName, txtLastName, txtICNumber, txtPhoneNumber;
    private Button btnSave;
    private FirebaseAuth auth;
    private DatabaseReference ref;

    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_details);

        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();

        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.etLastName);
        txtICNumber = findViewById(R.id.etICNumber);
        txtPhoneNumber = findViewById(R.id.etPhoneNumber);



        btnSave = findViewById(R.id.btnUserRegister);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            uid= null;
        } else {
            uid= extras.getString("uid");
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!txtFirstName.getText().toString().trim().isEmpty() && !txtLastName.getText().toString().trim().isEmpty() && !txtICNumber.getText().toString().trim().isEmpty() && !txtPhoneNumber.getText().toString().trim().isEmpty()) {

                    String firstname = txtFirstName.getText().toString().trim();
                    String lastname = txtLastName.getText().toString().trim();
                    String icnumber = txtICNumber.getText().toString().trim();
                    String phonenumber = txtPhoneNumber.getText().toString().trim();

                    Users user = new Users(firstname,lastname,icnumber,phonenumber);
                    DatabaseReference dr = ref.child("Users").child(uid).child("profile");
                    dr.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isComplete())
                            {
                                Toast.makeText(getApplicationContext(),"Welcome to JOM QR!",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Details is not saved",Toast.LENGTH_LONG).show();

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("registererror","Register Error");

                        }
                    });

                } else {

                    Toast.makeText(additionalDetails.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();

                }
            }
        });



    }
}
