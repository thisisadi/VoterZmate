package com.example.voterzmate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class VotingActivity extends AppCompatActivity {
    ImageView v1,v2,v3,v4;
    private FirebaseAuth mAuth;
    private FirebaseFirestore Fstore;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);

        v1 = findViewById(R.id.v1);
        v2 = findViewById(R.id.v2);
        v3 = findViewById(R.id.v3);
        v4 = findViewById(R.id.v4);
        mAuth = FirebaseAuth.getInstance();
        Fstore = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = Fstore.collection("users").document(userID);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VotingActivity.this);
                builder.setTitle("VoterZmate");
                builder.setMessage("Is this the Candidate you want to vote for ?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        documentReference.addSnapshotListener(VotingActivity.this, new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                                if(error==null)
                                {
                                    if(documentSnapshot.getLong("voteCount").intValue()==0) {
                                        documentReference.update("voteCount", FieldValue.increment(1));
                                        Fstore.collection("users").document(userID).update("votedCandidate", "A");
                                        dialog.dismiss();
                                    }
                                    else{
                                        Toast.makeText(VotingActivity.this, "You have already casted your vote!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VotingActivity.this);
                builder.setTitle("VoterZmate");
                builder.setMessage("Is this the Candidate you want to vote for ?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        documentReference.addSnapshotListener(VotingActivity.this, new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                                if(error==null)
                                {
                                    if(documentSnapshot.getLong("voteCount").intValue()==0) {
                                        documentReference.update("voteCount", FieldValue.increment(1));
                                        Fstore.collection("users").document(userID).update("votedCandidate", "B");
                                        dialog.dismiss();
                                    }
                                    else{
                                        Toast.makeText(VotingActivity.this, "You have already casted your vote!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VotingActivity.this);
                builder.setTitle("VoterZmate");
                builder.setMessage("Is this the Candidate you want to vote for ?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        documentReference.addSnapshotListener(VotingActivity.this, new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                                if(error==null)
                                {
                                    if(documentSnapshot.getLong("voteCount").intValue()==0) {
                                        documentReference.update("voteCount", FieldValue.increment(1));
                                        Fstore.collection("users").document(userID).update("votedCandidate", "C");
                                        dialog.dismiss();
                                    }
                                    else{
                                        Toast.makeText(VotingActivity.this, "You have already casted your vote!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VotingActivity.this);
                builder.setTitle("VoterZmate");
                builder.setMessage("Is this the Candidate you want to vote for ?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        documentReference.addSnapshotListener(VotingActivity.this, new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                                if(error==null)
                                {
                                    if(documentSnapshot.getLong("voteCount").intValue()==0) {
                                        documentReference.update("voteCount", FieldValue.increment(1));
                                        Fstore.collection("users").document(userID).update("votedCandidate", "D");
                                        dialog.dismiss();
                                    }
                                    else{
                                        Toast.makeText(VotingActivity.this, "You have already casted your vote!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

}