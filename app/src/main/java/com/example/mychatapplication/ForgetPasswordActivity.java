package com.example.mychatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {

    private TextInputEditText forgetMail;
    private Button resetbutton;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        forgetMail = findViewById(R.id.forgetMail);
        resetbutton = findViewById(R.id.resetbutton);
        auth = FirebaseAuth.getInstance();



        resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = forgetMail.getText().toString();
                if(mail != "")
                {
                    resetPassword(mail);
                }
                else
                {
                    Toast.makeText(ForgetPasswordActivity.this, "Tyr again later", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void resetPassword(String mail)
    {
        auth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(ForgetPasswordActivity.this, "Check your mail for reset password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ForgetPasswordActivity.this, "Something went wrong try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}