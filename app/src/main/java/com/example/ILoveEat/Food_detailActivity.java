package com.example.ILoveEat;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class Food_detailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
    }
    public void Startwritecomment(View view)
    {
        Intent intent=new Intent(this,WritecommentActivity.class);
        startActivity(intent);
    }

}
