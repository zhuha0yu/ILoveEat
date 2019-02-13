package com.example.ILoveEat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity implements Explore_main.OnFragmentInteractionListener,Messages_main.OnFragmentInteractionListener,Profile_main.OnFragmentInteractionListener{
    private FragmentTransaction transaction;

    private TextView mTextMessage;
    private FragmentManager fm;
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
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("message");

                    myRef.setValue("Hello, World!");
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

        SharedPreferences sp=getSharedPreferences ("LoginDetails", MODE_PRIVATE);
        Boolean LoginExists=sp.getBoolean("IfLogin",false);
        String userEmail=sp.getString("UserEmail","");
        if(!LoginExists)
        {
            startlogin();
        }
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        explore_main=new Explore_main();
        profile_main=new Profile_main();
        messages_main=new Messages_main();
        fm=getSupportFragmentManager();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.layout_fragment_main,explore_main);
        transaction.add(R.id.layout_fragment_main,profile_main);
        transaction.add(R.id.layout_fragment_main,messages_main);
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


public void startlogin()
{
    Intent intent = new Intent(this, LoginActivity.class);

    startActivity(intent);
}




}
