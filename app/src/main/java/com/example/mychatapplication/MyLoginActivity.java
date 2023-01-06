package com.example.mychatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyLoginActivity extends AppCompatActivity {

    private TextInputEditText mail,password;
    private Button signin,signup;
    private TextView forget;
    private FirebaseAuth auth;
    FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null)
        {
            Intent i = new Intent(MyLoginActivity.this,MainActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_login);

        mail = findViewById(R.id.editTextMail);
        password = findViewById(R.id.editTextPassword);
        signin = findViewById(R.id.ButtonSignin);
        signup = findViewById(R.id.buttonSignup);
        forget = findViewById(R.id.forgetPassword);
        auth = FirebaseAuth.getInstance();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String EntredMail = mail.getText().toString();
                String EntredPassword = password.getText().toString();

                if(EntredMail != "" && EntredPassword != "")
                {
                    SignIn(EntredMail,EntredPassword);
                }
                else
                {
                    Toast.makeText(MyLoginActivity.this, "Please enter mail id and password", Toast.LENGTH_SHORT).show();
                }

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyLoginActivity.this,SignUpActivity.class);
                startActivity(i);
            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyLoginActivity.this,ForgetPasswordActivity.class);
                startActivity(i);
            }
        });
    }

    public  void SignIn(String usermail,String userpassword)
    {
        auth.signInWithEmailAndPassword(usermail,userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    Intent i = new Intent(MyLoginActivity.this,MainActivity.class);
                    Toast.makeText(MyLoginActivity.this, "Successful login", Toast.LENGTH_SHORT).show();
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(MyLoginActivity.this, "Fail to login", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}