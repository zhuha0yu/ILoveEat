package com.example.ILoveEat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.ILoveEat.view.AlertView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Random;

public class WritecommentActivity extends AppCompatActivity {
    private RatingBar mRatingbar_overall;
    private RatingBar mRatingbar_Sweet;
    private RatingBar mRatingbar_Spicy;
    private RatingBar mRatingbar_Salty;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writecomment);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        mAuth=FirebaseAuth.getInstance();
        mRatingbar_overall=findViewById(R.id.ratingBar4);
        mRatingbar_Sweet=findViewById(R.id.ratingBar);
        mRatingbar_Spicy=findViewById(R.id.ratingBar2);
        mRatingbar_Salty=findViewById(R.id.ratingBar3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_addcomment, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        submit();

        return true;
    }
    public void submit()
    {
        Random random=new Random();
        Integer cid=random.nextInt();
        Comment comment=new Comment(0,mAuth.getCurrentUser().getUid(), cid.toString(),"","","",System.currentTimeMillis(),
                mRatingbar_Spicy.getRating(),mRatingbar_Sweet.getRating(),mRatingbar_Salty.getRating(),mRatingbar_overall.getRating());

        String dishid=(String) getIntent().getSerializableExtra("dishid");
        DocumentReference docref=db.collection("dishes").document(dishid);
        docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
           if(task.isSuccessful())
           {
               List<String> commentid=(List<String>)task.getResult().get("commentid");
               commentid.add(cid.toString());
               addcomment(comment,cid);
           }
            }
        });


    }
    public void addcomment(Comment comment,Integer cid)
    {
        DocumentReference docref=db.collection("comment").document(cid.toString());
        docref.set(comment).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                ShowComplete();
            }
        });
    }

    private void ShowComplete() {
        new AlertDialog.Builder(this)
                .setTitle("Comment submit Successful!")
                .setMessage("Comment submit Successful!")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })

                .show();
    }

}
