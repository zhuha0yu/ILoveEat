package com.example.ILoveEat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tbruyelle.rxpermissions.RxPermissions;
import rx.functions.Action1;


public class MainActivity extends AppCompatActivity implements Explore_main.OnFragmentInteractionListener, Messages_main.OnFragmentInteractionListener, Profile_main.OnFragmentInteractionListener {
    private FragmentTransaction transaction;
    private FirebaseAuth mAuth;

    private Explore_main explore_main;
    private Profile_main profile_main;
    private Messages_main messages_main;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.show(explore_main);
                    transaction.hide(profile_main);
                    transaction.hide(messages_main);
                    transaction.commit();

                    return true;
                case R.id.navigation_dashboard:
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.hide(explore_main);
                    transaction.show(messages_main);
                    transaction.hide(profile_main);
                    transaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.hide(explore_main);
                    transaction.hide(messages_main);
                    transaction.show(profile_main);
                    transaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
/*
        SharedPreferences sp=getSharedPreferences ("LoginDetails", MODE_PRIVATE);
        Boolean LoginExists=sp.getBoolean("IfLogin",false);
        String userEmail=sp.getString("UserEmail","");
        */
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            startlogin();
        }
        setContentView(R.layout.activity_main);


        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        explore_main = new Explore_main();
        profile_main = new Profile_main();
        messages_main = new Messages_main();

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.layout_fragment_main, explore_main);
        transaction.add(R.id.layout_fragment_main, profile_main);
        transaction.add(R.id.layout_fragment_main, messages_main);
        transaction.hide(profile_main);
        transaction.hide(messages_main);
        transaction.commit();


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?

            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    10);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    public void onStart()
    {
        if( !checkPermission(Manifest.permission.ACCESS_FINE_LOCATION))
            startRequestrReadPermision();
        super.onStart();
    }

    public void startlogin() {
        Intent intent = new Intent(this, LoginActivity.class);

        startActivity(intent);
    }
    private boolean checkPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                Log.e("checkPermission", "PERMISSION_GRANTED" + ContextCompat.checkSelfPermission(this, permission));
                return true;
            } else {
                Log.e("checkPermission", "PERMISSION_DENIED" + ContextCompat.checkSelfPermission(this, permission));
                return false;
            }
        } else {
            Log.e("checkPermission", "M以下" + ContextCompat.checkSelfPermission(this, permission));
            return true;
        }
    }
    private void startRequestrReadPermision() {
        RxPermissions.getInstance(MainActivity.this)
                .request(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION)//多个权限用","隔开
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                        } else {
                            return;
                        }
                    }
                });
    }

}
