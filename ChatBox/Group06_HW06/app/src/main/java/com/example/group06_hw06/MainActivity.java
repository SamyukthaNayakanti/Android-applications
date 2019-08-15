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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null)
        {
            //user logged in
            finish();
            Intent i = new Intent(MainActivity.this,ChatroomActivitiy.class);
            startActivity(i);
        }
        ((Button)findViewById(R.id.button_signup_login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,SignupActivity.class);
                startActivity(i);
                finish();
            }
        });
        ((Button)findViewById(R.id.button_login_login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email = (EditText)findViewById(R.id.editText_email_login);
                EditText password = (EditText)findViewById(R.id.editText_pwd_login);
                if(email.getText().toString().isEmpty()) {
                    email.setError("Please enter email");
                    return;
                }
                if(password.getText().toString().isEmpty()) {
                    password.setError("Please enter Password");
                    return;
                }

                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("Meghana", "signInWithEmail:success");
                                    Toast.makeText(MainActivity.this, "Authentication Successful.",
                                            Toast.LENGTH_LONG).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    finish();
                                    Intent i = new Intent(MainActivity.this,ChatroomActivitiy.class);
                                    String uname = user.getUid();
                                    startActivity(i);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.d("Meghana", "signInWithEmail:failure");
                                    Toast.makeText(MainActivity.this, "Authentication failed."+task.getException(),
                                            Toast.LENGTH_LONG).show();
                                }

                            }
                        });

            }
        });

    }
}
