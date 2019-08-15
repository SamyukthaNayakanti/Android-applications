package com.example.group06_hw06;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.PrintWriter;
import java.io.StringWriter;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        ((Button)findViewById(R.id.button_cancel_signup)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        ((Button)findViewById(R.id.button_signup_signup)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = ((EditText) findViewById(R.id.editText_fname_signup)).getText().toString();
                if (fname.isEmpty()) {
                    ((EditText) findViewById(R.id.editText_fname_signup)).setError("Enter fname");
                    return;
                }
                String lname = ((EditText) findViewById(R.id.editText_lname_signup)).getText().toString();
                if (lname.isEmpty()) {
                    ((EditText) findViewById(R.id.editText_lname_signup)).setError("Enter lname");
                    return;
                }
                String email = ((EditText) findViewById(R.id.editText_email_signup)).getText().toString();
                if (email.isEmpty()) {
                    ((EditText) findViewById(R.id.editText_email_signup)).setError("Enter email");
                    return;
                }
                String pass = ((EditText) findViewById(R.id.editText_pwd_signup)).getText().toString();
                if (pass.isEmpty()) {
                    ((EditText) findViewById(R.id.editText_pwd_signup)).setError("Enter password");
                    return;
                }

                String cfnpass = ((EditText) findViewById(R.id.editText_cfmpwd_signup)).getText().toString();
                if (cfnpass.isEmpty()) {
                    ((EditText) findViewById(R.id.editText_cfmpwd_signup)).setError("Enter password");
                    return;
                }

                if (!pass.equals(cfnpass)) {
                    Toast.makeText(SignupActivity.this, "Passwords doesnot match", Toast.LENGTH_LONG).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("Meghana", "createUserWithEmail:success");
                                    Toast.makeText(SignupActivity.this, "Authentication Successfull",
                                            Toast.LENGTH_LONG).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    finish();
                                    Intent i = new Intent(SignupActivity.this, ChatroomActivitiy.class);
                                    startActivity(i);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("Meghana", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_LONG).show();

                                }

                            }
                        });
                FirebaseUser user = mAuth.getCurrentUser();

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(fname+" "+lname)
                        .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("Meghana", "User profile updated.");
                                }
                            }
                        });
            }
        });

    }
}
