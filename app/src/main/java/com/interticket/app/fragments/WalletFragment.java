package com.interticket.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.interticket.app.MainActivity;
import com.interticket.app.R;
import com.interticket.app.constants.Constants;

public class WalletFragment extends Fragment {
    private TextView txtBalance;
    private Button  btnRecharge, btnCancel, btnSave;
    private TextInputEditText   etAmount;
    private DatabaseReference ref;
    private FirebaseAuth auth;
    private String amount;
    private Constants constants;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet_profile, container, false);

        ref = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        txtBalance = view.findViewById(R.id.txtBalance);

        btnRecharge = view.findViewById(R.id.btnRecharge);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnSave = view.findViewById(R.id.btnSave);

        etAmount = view.findViewById(R.id.etAmount);

        ref.child("Users").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(snapshot.getKey().equalsIgnoreCase("balance")){
                        txtBalance.setText("RM " + snapshot.getValue());
                        amount = snapshot.getValue(String.class);
                        ((MainActivity)getActivity()).setAmount(amount);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCancel.setVisibility(View.VISIBLE);
                btnSave.setVisibility(View.VISIBLE);
                etAmount.setVisibility(View.VISIBLE);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSave.setVisibility(View.GONE);
                btnCancel.setVisibility(View.GONE);
                etAmount.setVisibility(View.GONE);
                etAmount.setText("");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etAmount.getText().toString().trim().isEmpty()){

                    float topupAmount = Float.parseFloat(etAmount.getText().toString());
                    float currentAmount = Float.parseFloat(amount);
                    float totalAmount = topupAmount + currentAmount;
                    amount = String.valueOf(totalAmount);

                    ref.child("Users").child(auth.getCurrentUser().getUid()).child("balance").setValue(amount);
                    Toast.makeText(getContext(), "Your wallet have been recharged!", Toast.LENGTH_SHORT).show();
                    btnSave.setVisibility(View.GONE);
                    btnCancel.setVisibility(View.GONE);
                    etAmount.setVisibility(View.GONE);
                    etAmount.setText("");
                    txtBalance.setText("RM " + amount);
                    ((MainActivity)getActivity()).setAmount(amount);
                }
            }
        });
        return view;

    }

}
