package com.example.test2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Console;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

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

    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }


public void startlogin()
{
    Intent intent = new Intent(this, LoginActivity.class);

    startActivity(intent);
}
    public void btn_signin_out_method(View view)
    {
        Intent intent_login=new Intent(this,LoginActivity.class);
        startActivity(intent_login);
    }


}
