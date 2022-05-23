package com.interticket.app.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.interticket.app.LoginActivity;
import com.interticket.app.MainActivity;
import com.interticket.app.R;
import com.interticket.app.Users;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TextInputEditText etEmail, etPhoneNumber, etFirstName, etLastName,  etICNumber;
    private Button btnEdit, btnSignOut;
    private DatabaseReference ref;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private FirebaseAuth uid;
    private Switch aSwitch;
    private TextView textView;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        ref = FirebaseDatabase.getInstance().getReference();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        user.getEmail();
        String uid = user.getEmail();


        etEmail = view.findViewById(R.id.etEmail);
        etFirstName = view.findViewById(R.id.etFirstName);
        etLastName = view.findViewById(R.id.etLastName);
        etICNumber = view.findViewById(R.id.etICNumber);
        etPhoneNumber = view.findViewById(R.id.etPhoneNumber);

        textView = view.findViewById(R.id.theme_label);
        aSwitch = view.findViewById(R.id.mode);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            aSwitch.setChecked(true);
        }

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                reset();
            }
        });

        btnEdit = view.findViewById(R.id.btnEdit);
        btnSignOut = view.findViewById(R.id.btnSignOut);

        etEmail.setText(uid);


        DatabaseReference dr = ref.child("Users").child(user.getUid()).child("profile");
        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Users user = dataSnapshot.getValue(Users.class);
                    etFirstName.setText(user.getFirstName());
                    etLastName.setText(user.getLastName());
                    etICNumber.setText(user.getIcNumber());
                    etPhoneNumber.setText(user.getPhoneNumber());


                }

                else
                {
                    Toast.makeText(getContext(),"Please Update Your Profile",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(btnEdit.getText().toString().equalsIgnoreCase("Edit Profile")){
                    etEmail.setEnabled(false);
                    etFirstName.setEnabled(true);
                    etLastName.setEnabled(true);
                    etICNumber.setEnabled(true);
                    etPhoneNumber.setEnabled(true);
                    btnEdit.setText("Save");

                }else{
                    Users userprofile = new Users(etFirstName.getText().toString(), etLastName.getText().toString(), etICNumber.getText().toString(), etPhoneNumber.getText().toString());
                    ref.child("Users").child(user.getUid()).child("profile").setValue(userprofile);
                    btnEdit.setText("Edit Profile");
                    etEmail.setEnabled(false);
                    etFirstName.setEnabled(false);
                    etLastName.setEnabled(false);
                    etICNumber.setEnabled(false);
                    etPhoneNumber.setEnabled(false);
                    Toast.makeText(getContext(), "Your profile have been updated!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                Toast.makeText(getContext(), "Sign Out", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    private void reset() {
        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

}
