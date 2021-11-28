package com.example.voterzmate.ui.gallery;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.example.voterzmate.R;
import com.example.voterzmate.VotingActivity;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.util.concurrent.Executor;

public class GalleryFragment extends Fragment {
    String myChildText;
    Firebase myFirebase;
    ImageView button;
    TextView txt;
    ImageView img1;
    private Executor executor;
    private BiometricPrompt.PromptInfo promptInfo;
    private BiometricPrompt biometricPrompt;
    private static final int REQUEST_CODE = 1;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        txt = view.findViewById(R.id.textView);
        button = view.findViewById(R.id.voting_scan);
        img1 = view.findViewById(R.id.voting_results);



        button.setVisibility(View.GONE);
        Firebase.setAndroidContext(getContext());
        myFirebase = new Firebase("https://voterzmate-default-rtdb.firebaseio.com/OngoingElections");
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
                if (myChildText.equals("startVoting"))
                {
                    img1.setImageResource(R.drawable.voting_time);
                    button.setVisibility(View.VISIBLE);

                    BiometricManager biometricManager = BiometricManager.from(getActivity());
                    switch (biometricManager.canAuthenticate(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)) {
                        case BiometricManager.BIOMETRIC_SUCCESS:
                            Log.d("MY_APP_TAG", "App can authenticate using biometrics.");
                            break;
                        case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                            Toast.makeText(getActivity(), "Fingerprint Sensor not available !", Toast.LENGTH_SHORT).show();
                            break;
                        case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                            Toast.makeText(getActivity(), "Sensor not available or busy !", Toast.LENGTH_SHORT).show();
                            break;
                        case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                            // Prompts the user to create credentials that your app accepts.
                            final Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                            enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                                    BIOMETRIC_STRONG | DEVICE_CREDENTIAL);
                            startActivityForResult(enrollIntent, REQUEST_CODE);
                            break;
                        case BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED:
                            break;
                        case BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED:
                            break;
                        case BiometricManager.BIOMETRIC_STATUS_UNKNOWN:
                            break;
                    }

                    executor = ContextCompat.getMainExecutor(getActivity());
                    biometricPrompt = new BiometricPrompt(getActivity(),
                            executor, new BiometricPrompt.AuthenticationCallback() {
                        @Override
                        public void onAuthenticationError(int errorCode,
                                                          @NonNull CharSequence errString) {
                            super.onAuthenticationError(errorCode, errString);
                            Toast.makeText(getContext(),
                                    "Authentication error: " + errString, Toast.LENGTH_SHORT)
                                    .show();
                        }

                        @Override
                        public void onAuthenticationSucceeded(
                                @NonNull BiometricPrompt.AuthenticationResult result) {
                            super.onAuthenticationSucceeded(result);
                            Toast.makeText(getContext(), "Authentication succeeded!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getActivity(), VotingActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }

                        @Override
                        public void onAuthenticationFailed() {
                            super.onAuthenticationFailed();
                            Toast.makeText(getContext(), "Authentication failed",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });

                    promptInfo = new BiometricPrompt.PromptInfo.Builder()
                            .setTitle("Biometric login for VoterZmate ")
                            .setSubtitle("Log in using your biometric credentials")
                            .setNegativeButtonText("Use account password")
                            .build();

                    // Prompt appears when user clicks "Log in".
                    // Consider integrating with the keystore to unlock cryptographic operations,
                    // if needed by your app.

                    button.setOnClickListener(view -> {
                        biometricPrompt.authenticate(promptInfo);
                    });
                }

                else if (myChildText.equals("stopVoting")) {
                    img1.setImageResource(R.drawable.ongoing_elections_bg);
                    button.setVisibility(View.GONE);
                }

                else if (myChildText.equals("Reset")) {

                }
                else
                {   img1.setImageResource(R.drawable.ongoing_elections_bg);
                    button.setVisibility(View.GONE);
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