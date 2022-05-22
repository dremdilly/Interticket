package com.interticket.app.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;
import com.interticket.app.MainActivity;
import com.interticket.app.R;
import com.interticket.app.models.History;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyFragment extends Fragment {
    float currentAmount;
    float resultAmount;
    private CodeScanner mCodeScanner;
    private FirebaseAuth auth;
    private DatabaseReference ref;
    private History history;


    public BuyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buy, container, false);
        final Activity activity = getActivity();
        CodeScannerView scannerView = view.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(getContext(), scannerView);
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        history = new History();

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        resultAmount = Float.parseFloat(result.getText());

                        DatabaseReference dr = ref.child("Users").child(auth.getCurrentUser().getUid()).child("balance");
                        dr.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    String stest = dataSnapshot.getValue(String.class);
                                    float test = Float.parseFloat(stest);
                                    setCurrentamout(test);

                                    Toast.makeText(getContext(), "current amount is " + currentAmount, Toast.LENGTH_LONG).show();

                                    if (currentAmount >= resultAmount && resultAmount != 0) {
                                        float balance = currentAmount - resultAmount;

                                        ref.child("Users").child(auth.getCurrentUser().getUid()).child
                                                ("balance").setValue(String.valueOf(balance));

                                        history.setAmount(result.getText().trim());
                                        history.setBalance(String.valueOf(balance));


                                        if (balance != currentAmount && balance < currentAmount) {
                                            ref.child("History").child(auth.getCurrentUser().getUid()).push().setValue(history);
                                            ((MainActivity) getActivity()).setAmount(String.valueOf(balance));
                                            Toast.makeText(activity, "The payment is successful!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(activity, "Balance is too low", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }


                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    private void setCurrentamout(float amt) {
        currentAmount = amt;
    }

}
