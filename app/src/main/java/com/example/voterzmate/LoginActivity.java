package com.example.voterzmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText email_but,password_but;
    ImageView login_but,sign_up_but;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (isSignedIn())
        {
            moveToSecondary();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        // Removing the Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        email_but = findViewById(R.id.email_but);
        password_but = findViewById(R.id.password_but);
        login_but = findViewById(R.id.login_but);
        sign_up_but = findViewById(R.id.sign_up_but);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        sign_up_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        login_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
            }
        });
    }
    private void performLogin(){
        String EMAIL = email_but.getText().toString();
        String pw = password_but.getText().toString();
        if (!EMAIL.matches(emailPattern))
        {
            email_but.setError("Enter Correct Email !");
            email_but.requestFocus();
        }else if (pw.isEmpty() || pw.length() < 6)
        {
            password_but.setError("Enter a proper Password of atleast 6 characters");
            password_but.requestFocus();
        }
        else {
            progressDialog.setMessage("Logging In...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(EMAIL,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Please scan your fingerprint !", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(LoginActivity.this,FingerprintActivity.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent2);
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private boolean isSignedIn() {
        if (FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            return true;
        }
        return false;
    }

    public void moveToSecondary(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}