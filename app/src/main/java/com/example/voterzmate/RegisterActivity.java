package com.example.voterzmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText name,aadhaar_number,email,password,confirmpassword;
    private ImageView  create_acct_but;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseFirestore Fstore;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        // Removing the Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        name = findViewById(R.id.name);
        aadhaar_number = findViewById(R.id.aadhaar_number);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        create_acct_but = findViewById(R.id.create_acct_but);
        confirmpassword = findViewById(R.id.confirmPassword);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        Fstore = FirebaseFirestore.getInstance();
        create_acct_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAuth();
            }
        });
    }
    private void performAuth()
    {
        String AADHAAR = aadhaar_number.getText().toString();
        String NAME = name.getText().toString();
        String EMAIL = email.getText().toString();
        String pw = password.getText().toString();
        String cfm_pw = confirmpassword.getText().toString();
        if (!EMAIL.matches(emailPattern))
        {
            email.setError("Enter Correct Email !");
            email.requestFocus();
        }else if (pw.isEmpty() || pw.length() < 6)
        {
            password.setError("Enter a proper Password of atleast 6 characters");
            password.requestFocus();
        }
        else if (!pw.equals(cfm_pw))
        {
            confirmpassword.setError("Password doesn't match !");
            confirmpassword.requestFocus();
        }
        else if (AADHAAR.length()!=12)
        {
            aadhaar_number.setError("Enter a 12 digit valid Aadhaar Number !");
            aadhaar_number.requestFocus();
        }
        else
        {
            progressDialog.setMessage("Please Wait...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(EMAIL,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Please scan your fingerprint !", Toast.LENGTH_SHORT).show();
                        userID = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = Fstore.collection("users").document(userID);
                        Map<String,Object> user = new HashMap<>();
                        user.put("fName",NAME);
                        user.put("email",EMAIL);
                        user.put("aadhaar",AADHAAR);
                        user.put("votedCandidate","null");
                        user.put("voteCount",0);
                        user.put("A",0);
                        user.put("B",0);
                        user.put("C",0);
                        user.put("D",0);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("user","onSuccess: User profile created for "+userID);
                            }
                        });
                        Intent intent2 = new Intent(RegisterActivity.this,FingerprintActivity.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent2);
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}