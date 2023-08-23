package org.teddy.authentication_android_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    TextInputEditText textEditEmail, textEditPassword;
    Button btnLogin;

    ProgressBar progressBar;
    FirebaseAuth mAuth;
    TextView haveAccount;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent  = new Intent (getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        textEditEmail = findViewById(R.id.email);
        textEditPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressBar);
        haveAccount = findViewById(R.id.noAccount);

        haveAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startRegistrationActivity();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(textEditEmail.getText());
                password = String.valueOf(textEditPassword.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(Login.this, "Please Enter Your Text", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Login.this, "Please Enter Your Text", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this, "Welcome", Toast.LENGTH_SHORT).show();
                                    Intent intent  = new Intent (Login.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    //                                    FirebaseUser user = mAuth.getCurrentUser();
                                } else {

                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }

    public void startRegistrationActivity(){
        Intent intent = new Intent(Login.this, Registration.class);
        startActivity(intent);
    }
}