package com.example.voterzmate.ui.slideshow;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.voterzmate.R;
import com.example.voterzmate.VotingActivity;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class SlideshowFragment extends Fragment {
    String myChildText;
    Firebase myFirebase;
    TextView txt,result;
    ImageView img,title;
    private FirebaseAuth mAuth;
    private FirebaseFirestore Fstore;
    private String userID;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);
        txt = view.findViewById(R.id.resultTxt);
        result = view.findViewById(R.id.textView2);
        img = view.findViewById(R.id.voting_results);
        title = view.findViewById(R.id.election_title);
        ProgressBar p1 = view.findViewById(R.id.ProgressBar1);
        p1.setVisibility(View.GONE);
        TextView txt1 = view.findViewById(R.id.txt1);
        txt1.setVisibility(View.GONE);
        ProgressBar p2 = view.findViewById(R.id.ProgressBar2);
        p2.setVisibility(View.GONE);
        TextView txt2 = view.findViewById(R.id.txt2);
        txt2.setVisibility(View.GONE);
        ProgressBar p3 = view.findViewById(R.id.ProgressBar3);
        p3.setVisibility(View.GONE);
        TextView txt3 = view.findViewById(R.id.txt3);
        txt3.setVisibility(View.GONE);
        ProgressBar p4 = view.findViewById(R.id.ProgressBar4);
        p4.setVisibility(View.GONE);
        TextView txt4 = view.findViewById(R.id.txt4);
        txt4.setVisibility(View.GONE);
        result.setVisibility(View.GONE);
        Firebase.setAndroidContext(getContext());
        myFirebase = new Firebase("https://voterzmate-default-rtdb.firebaseio.com/ElectionResults");
        myFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myChildText = dataSnapshot.getValue(String.class);
                txt.setText(myChildText);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                txt.setText(firebaseError.toString());
            }
        });
        txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                mAuth = FirebaseAuth.getInstance();
                Fstore = FirebaseFirestore.getInstance();
                userID = mAuth.getCurrentUser().getUid();
                DocumentReference documentReference = Fstore.collection("users").document(userID);
                if (myChildText.equals("displayResult")) {
                    img.setImageResource(R.drawable.voting_results);
                    title.setVisibility(View.GONE);
                    p1.setVisibility(View.VISIBLE);
                    txt1.setVisibility(View.VISIBLE);
                    p2.setVisibility(View.VISIBLE);
                    txt2.setVisibility(View.VISIBLE);
                    p3.setVisibility(View.VISIBLE);
                    txt3.setVisibility(View.VISIBLE);
                    p4.setVisibility(View.VISIBLE);
                    txt4.setVisibility(View.VISIBLE);
                    result.setVisibility(View.VISIBLE);
                    p1.setProgress(30);
                    txt1.setText("30%                 A");
                    p2.setProgress(20);
                    txt2.setText("20%                 B");
                    p3.setProgress(15);
                    txt3.setText("15%                 C");
                    p4.setProgress(35);
                    txt4.setText("35%                 D");
                         FirebaseFirestore.getInstance()
                                 .collection("users")
                                 .whereEqualTo("votedCandidate","A")
                                 .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                     @Override
                                     public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                     }
                                 });

                    FirebaseFirestore.getInstance()
                            .collection("users")
                            .whereEqualTo("votedCandidate","B")
                            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        }
                    });

                    FirebaseFirestore.getInstance()
                            .collection("users")
                            .whereEqualTo("votedCandidate","C")
                            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        }
                    });

                    FirebaseFirestore.getInstance()
                            .collection("users")
                            .whereEqualTo("votedCandidate","D")
                            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        }
                    });


                    documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                            if(error==null)
                            {
                                //p1.setProgress(((documentSnapshot.getLong("A").intValue())/(documentSnapshot.getLong("A").intValue()+documentSnapshot.getLong("B").intValue()+documentSnapshot.getLong("C").intValue()+documentSnapshot.getLong("D").intValue()))*100);
                                //p2.setProgress(((documentSnapshot.getLong("B").intValue())/(documentSnapshot.getLong("A").intValue()+documentSnapshot.getLong("B").intValue()+documentSnapshot.getLong("C").intValue()+documentSnapshot.getLong("D").intValue()))*100);
                                //p3.setProgress(((documentSnapshot.getLong("C").intValue())/(documentSnapshot.getLong("A").intValue()+documentSnapshot.getLong("B").intValue()+documentSnapshot.getLong("C").intValue()+documentSnapshot.getLong("D").intValue()))*100);
                                //p4.setProgress(((documentSnapshot.getLong("D").intValue())/(documentSnapshot.getLong("A").intValue()+documentSnapshot.getLong("B").intValue()+documentSnapshot.getLong("C").intValue()+documentSnapshot.getLong("D").intValue()))*100);
                            }
                        }
                    });


                }
                else if (myChildText.equals("Reset")) {

                }
                else {
                    img.setImageResource(R.drawable.election_results_bg);
                    title.setVisibility(View.VISIBLE);
                    p1.setVisibility(View.GONE);
                    txt1.setVisibility(View.GONE);
                    p2.setVisibility(View.GONE);
                    txt2.setVisibility(View.GONE);
                    p3.setVisibility(View.GONE);
                    txt3.setVisibility(View.GONE);
                    p4.setVisibility(View.GONE);
                    txt4.setVisibility(View.GONE);
                    result.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}