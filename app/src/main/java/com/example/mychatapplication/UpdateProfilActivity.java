package com.example.mychatapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateProfilActivity extends AppCompatActivity {

    private CircleImageView updateimage;
    private TextInputEditText updateusername;
    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profil);

        updateimage = findViewById(R.id.UpdatecircleImageView);
        updateusername = findViewById(R.id.UpdateSignupUsername);
        update = findViewById(R.id.Updatebutton);

    }
}