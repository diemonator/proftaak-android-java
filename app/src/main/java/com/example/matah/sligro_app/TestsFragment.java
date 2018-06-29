package com.example.matah.sligro_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
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
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;

public class TestsFragment extends Fragment {
    /*private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String TAG;*/

    private DatabaseReference myRef;
    private StorageReference myStorageRef;
    static File localFile;
    static int i;

    public TestsFragment() {
        // Required empty public constructor
    }

    //a lot of this is hardcoded for demo purposes. Wasn't my desire to leave it like that,
    //but a huge variety of all sorts of weird types issues were encountered and it made more sense
    //to go for ugly code but ok representation since we are doing somewhat of a proof of concept
    //and not really delivering a real product. Apologies for the hardcode regardless.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        final View v = inflater.inflate(R.layout.fragment_tests, container, false);
        myRef = FirebaseDatabase.getInstance().getReference();
        myStorageRef = FirebaseStorage.getInstance().getReference();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        myRef.child("users").child(user.getUid()).child("testResults").child("1").child("score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int testScore = Integer.parseInt(dataSnapshot.getValue().toString());

                CircularProgressBar circularProgressBar = (CircularProgressBar) v.findViewById(R.id.circularProgressBar);
                TextView tv = (TextView) v.findViewById(R.id.circularProgressBarText);
                updateScore(circularProgressBar, tv, testScore);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        myRef.child("tests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                StorageReference pathRef;
                TextView tv;

                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Log.d("MY_TAG", "asd" + Integer.parseInt(snap.getKey().toString()));
                    Log.d("MY_TAG", snap.child("name").getValue().toString());
                    Log.d("MY_TAG", snap.child("thumb").getValue().toString());

                    i = Integer.parseInt(snap.getKey().toString());

                    if (i == 1) {
                        tv = (TextView) v.findViewById(R.id.textView1);
                    } else {
                        tv = (TextView) v.findViewById(R.id.textView2);
                    }

                    String asd = snap.child("name").getValue().toString();
                    tv.setText(asd);

                    pathRef = myStorageRef.child(snap.child("thumb").getValue().toString());
                    localFile = null;

                    try {
                        localFile = File.createTempFile("testThumbnail", ".jpg");
                        pathRef.getFile(localFile)
                                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        // Successfully downloaded data to local file
                                        Bitmap bmp = BitmapFactory.decodeFile(localFile.getPath());
                                        ImageView imgv = (ImageView) v.findViewById(R.id.imageView2);
                                        imgv.setImageBitmap(bmp);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle failed download
                                Log.d("MY_TAG", exception.getMessage());
                                //imgv = new ImageView(getContext());
                                //imgv.setImageResource(R.drawable.ic_guide_book);
                            }
                        });
                    } catch (IOException e) {
                        Log.e("MY_TAG", e.getMessage());
                    }


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("MY_TAG", "no poof paaf");
            }
        });



        CircularProgressBar circularProgressBar = (CircularProgressBar) v.findViewById(R.id.circularProgressBar2);
        TextView tv = (TextView) v.findViewById(R.id.circularProgressBarText2);
        updateScore(circularProgressBar, tv, 65);

        circularProgressBar = (CircularProgressBar) v.findViewById(R.id.circularProgressBar3);
        tv = (TextView) v.findViewById(R.id.circularProgressBarText3);
        updateScore(circularProgressBar, tv, 73);

        circularProgressBar = (CircularProgressBar) v.findViewById(R.id.circularProgressBar4);
        tv = (TextView) v.findViewById(R.id.circularProgressBarText4);
        updateScore(circularProgressBar, tv, 96);

        TableLayout tl = (TableLayout) v.findViewById((R.id.myTable));
        tl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showAParticularTest();
                startActivity(new Intent(getActivity(), TestParticularActivity.class));
            }
        });

        return v;
    }

    private void updateScore(CircularProgressBar circularProgressBar, TextView tv, int score) {
        circularProgressBar.setColor(ContextCompat.getColor(getActivity(), R.color.sligroYellow));
        circularProgressBar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        // circularProgressBar.setProgressBarWidth(33);
        // circularProgressBar.setBackgroundProgressBarWidth(22);
        int animationDuration = 2500; // 2500ms = 2,5s
        circularProgressBar.setProgressWithAnimation(score, animationDuration); // Default duration = 1500ms

        tv.setText(score + "%");
    }


}
