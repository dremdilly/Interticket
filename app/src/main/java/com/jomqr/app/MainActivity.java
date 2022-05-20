package com.jomqr.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jomqr.app.fragments.BuyFragment;
import com.jomqr.app.fragments.HistoryFragment;
import com.jomqr.app.fragments.ProfileFragment;
import com.jomqr.app.fragments.WalletFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bnPrincipal;
    private ProfileFragment profileFragment;
    private WalletFragment walletFragment;
    private HistoryFragment historyFragment;
    private BuyFragment buyFragment;
    private String amount = "0";
    private DatabaseReference ref;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bnPrincipal = findViewById(R.id.bnPrincipal);
        ref = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        ref.child("Users").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(snapshot.getKey().equalsIgnoreCase("balance")){
                        amount = snapshot.getValue().toString();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        bnPrincipal.setOnNavigationItemSelectedListener(this);
        openFragment(new ProfileFragment());

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.CAMERA)) {


            } else {
                ActivityCompat.requestPermissions( this,
                        new String[]{Manifest.permission.CAMERA},
                        1);
            }

        }

        profileFragment = new ProfileFragment();
        walletFragment = new WalletFragment();
        historyFragment = new HistoryFragment();
        buyFragment = new BuyFragment();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.menuProfile:
                openFragment(profileFragment);
                return true;
            case R.id.menuWallet:
                openFragment(walletFragment);
                return true;
            case R.id.menuHistory:
                openFragment(historyFragment);
                return true;
            case R.id.menuScanner:
                openFragment(buyFragment);
                return true;

        }

        return false;
    }

    public void openFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragments,fragment).addToBackStack(null).commit();
    }

    public void setAmount(String amount){
        amount = amount;
    }

    public String getAmount(){


        return amount;
    }
}
