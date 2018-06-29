package com.example.matah.sligro_app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TestParticularActivity extends AppCompatActivity {

    private StorageReference mStorageRef;
    private DatabaseReference myRef;
    static File localFile;
    static ArrayList<Question> questions;

    private CheckBox cb1, cb2, cb3, cb4;
    private RadioButton rb11, rb12, rb21, rb22;


    //same as before, automation was attempted and weird problems ensued.
    //if the questions array wasnt returning empty on line 103, this wouldn't have been hardcoded like that
    //apologies once again
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_particular);

        questions = new ArrayList<Question>();

        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("tests").child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setTitle(dataSnapshot.child("name").getValue().toString());

                TextView tv = (TextView) findViewById(R.id.introTV);
                tv.setText(dataSnapshot.child("intro").getValue().toString());

                mStorageRef = FirebaseStorage.getInstance().getReference();
                StorageReference pathRef = mStorageRef.child(dataSnapshot.child("image").getValue().toString());

                localFile = null;
                try {
                    localFile = File.createTempFile("phishingImage", ".jpg");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pathRef.getFile(localFile)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                // Successfully downloaded data to local file
                                Bitmap bmp = BitmapFactory.decodeFile(localFile.getPath());
                                ImageView imgv = (ImageView) findViewById(R.id.testImageView);
                                imgv.setImageBitmap(bmp);
                                imgv.setVisibility(View.VISIBLE);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle failed download
                        Log.d("MY_TAG", exception.getMessage());

                    }
                });


//                myRef.child("tests").child("1").child("questions").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        ArrayList<String> answers;
//                        int i = 0;
//                        for (DataSnapshot snap : dataSnapshot.getChildren()) {
//                            answers = new ArrayList<String>();
//
//                            for (DataSnapshot answ : snap.child("answers").getChildren()) {
//                                answers.add(snap.child("answers").child(snap.getKey()).getValue().toString());
//                            }
//                            Log.d("MY_TAG", i++ + "asd");
//
//                            questions.add(new Question(snap.getKey().toString(), snap.child("questionContent").toString(),
//                                    snap.child("correctAnswer").child("content").toString(),
//                                    snap.child("correctAnswer").child("index").toString(), answers));
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("MY_TAG", databaseError.getDetails().toString());
            }
        });

//        TableLayout table = (TableLayout) findViewById(R.id.myTable);
//        Log.d("MY_TAG", questions.size() + "ads");
//
//        for(Question q : questions){
//            Log.d("MY_TAG", q.getqContent());
//
//            TableRow tr = new TableRow(this);
//            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
//
//            TextView cont = new TextView(this);
//            cont.setText(q.getqContent());
//            cont.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
//
//            tr.addView(cont);
//
//            table.addView(tr);
//        }

        cb1 = (CheckBox) findViewById(R.id.checkBoxQ1);
        cb2 = (CheckBox) findViewById(R.id.checkBoxQ2);
        cb3 = (CheckBox) findViewById(R.id.checkBoxQ3);
        cb4 = (CheckBox) findViewById(R.id.checkBoxQ4);

        rb11 = (RadioButton) findViewById(R.id.radioButton1Q2);
        rb12 = (RadioButton) findViewById(R.id.radioButton2Q2);
        rb21 = (RadioButton) findViewById(R.id.radioButton1Q3);
        rb22 = (RadioButton) findViewById(R.id.radioButton2Q3);


        rb11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb11.setChecked(true);
                if(rb12.isChecked()){
                    rb12.setChecked(false);
                }
            }
        });

        rb12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb11.setChecked(true);
                if(rb11.isChecked()){
                    rb11.setChecked(false);
                }
            }
        });

        rb21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb21.setChecked(true);
                if(rb22.isChecked()){
                    rb22.setChecked(false);
                }
            }
        });

        rb22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb22.setChecked(true);
                if(rb21.isChecked()){
                    rb21.setChecked(false);
                }
            }
        });

    }

    public void onSubmit(View view) {
        boolean first = true;
        if (!cb1.isChecked()) {
            cb1.setTextColor(ContextCompat.getColor(this, R.color.red));
            first = false;
        } else {
            cb1.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        if (!cb2.isChecked()) {
            cb2.setTextColor(ContextCompat.getColor(this, R.color.red));
            first = false;
        } else {
            cb2.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        if (!cb3.isChecked()) {
            cb3.setTextColor(ContextCompat.getColor(this, R.color.red));
            first = false;
        } else {
            cb3.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        if (!cb4.isChecked()) {
            cb4.setTextColor(ContextCompat.getColor(this, R.color.red));
            first = false;
        } else {
            cb4.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        TextView q1answ = (TextView) findViewById(R.id.answerQ1);
        q1answ.setVisibility(View.VISIBLE);

        if (first) {
            q1answ.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        } else {
            q1answ.setTextColor(ContextCompat.getColor(this, R.color.red));
        }


        TextView q2answ = (TextView) findViewById(R.id.answerQ2);
        q2answ.setVisibility(View.VISIBLE);

        boolean second = true;

        if (!rb11.isChecked()) {
            rb11.setTextColor(ContextCompat.getColor(this, R.color.red));
            second = false;
            q2answ.setTextColor(ContextCompat.getColor(this, R.color.red));
        } else {
            rb11.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            q2answ.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        boolean third = true;

        TextView q3answ = (TextView) findViewById(R.id.answerQ3);
        q3answ.setVisibility(View.VISIBLE);

        if (!rb21.isChecked()) {
            rb21.setTextColor(ContextCompat.getColor(this, R.color.red));
            second = false;
            q3answ.setTextColor(ContextCompat.getColor(this, R.color.red));
        } else {
            rb21.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            q3answ.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        int finalScore = 0;
        if(first)
            finalScore = 33;

        if(second)
            finalScore += 33;

        if(third)
            finalScore += 34;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        myRef.child("users").child(user.getUid()).child("testResults").child("1").child("score").setValue(finalScore);
    }


}
