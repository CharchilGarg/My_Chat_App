package com.example.mychatapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpActivity extends AppCompatActivity {

    private CircleImageView circleImageView;
    private TextInputEditText signupMail,signupPasssword,signupUsername;
    private Button register;
    private boolean imagechecker = false;
    Uri imageuri;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        circleImageView = findViewById(R.id.UpdatecircleImageView);
        signupMail = findViewById(R.id.SignupEmail);
        signupPasssword = findViewById(R.id.SignupPassword);
        signupUsername = findViewById(R.id.UpdateSignupUsername);
        register = findViewById(R.id.Updatebutton);
        auth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = signupMail.getText().toString();
                String password = signupPasssword.getText().toString();
                String username = signupUsername.getText().toString();

                if(mail != "" && password != "" && username != "")
                {
                    Signup(mail,password,username);
                }
                else
                {
                    Toast.makeText(SignUpActivity.this, "Enter all the data", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void selectImage()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0 && resultCode == RESULT_OK && data != null)
        {
            imageuri = data.getData();
            Picasso.get().load(imageuri).into(circleImageView);
            imagechecker = true;
        }
        else
        {
            imagechecker = false;
        }
    }

    public void Signup(String mail,String password,String username)
    {
        auth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    reference.child("Users").child(auth.getUid()).child("UserName").setValue(username);

                    if(imagechecker)
                    {
                        UUID randomID = UUID.randomUUID();
                        final String imageName = "images/*"+randomID+".jpg";
                        storageReference.child(imageName).putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                StorageReference myreference = storage.getReference(imageName);
                                myreference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String filepath = uri.toString();
                                        reference.child("Users").child(auth.getUid()).child("image").setValue(filepath).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(SignUpActivity.this, "Successfully added data to database", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(SignUpActivity.this, "Failed to add data to database", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                    else
                    {
                        reference.child("Users").child(auth.getUid()).child("image").setValue(null);
                    }
                    Intent i = new Intent(SignUpActivity.this,MainActivity.class);
                    i.putExtra("username",username);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Toast.makeText(SignUpActivity.this, "Some thing went wrong try again", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}