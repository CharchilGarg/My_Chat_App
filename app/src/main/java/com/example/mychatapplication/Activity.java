package com.example.mychatapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Activity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private RecyclerView rv;
    private EditText message;
    private FloatingActionButton flo;
    FirebaseDatabase database;
    DatabaseReference reference;
    String username;
    String othername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_);

        imageView = findViewById(R.id.BackButton);
        textView = findViewById(R.id.otherNameTextView);
        rv = findViewById(R.id.rv);
        message = findViewById(R.id.message);
        flo = findViewById(R.id.sendButton);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        username = getIntent().getStringExtra("username");
        othername = getIntent().getStringExtra("othername");

        textView.setText(othername);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Activity.this,MainActivity.class);
                startActivity(i);
            }
        });

        flo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usermessage = message.getText().toString();

                if(!usermessage.equals(""))
                {
                    message.setText("");
                    sendMessage(usermessage);
                }
            }
        });

    }

    public void sendMessage(String message)
    {
        String key = reference.child("Message").child(username).child(othername).push().getKey();
        Map<String,Object> messageMap = new HashMap<>();
        messageMap.put("message",message);
        messageMap.put("from",username);
        reference.child("Message").child(username).child(othername).child(key).setValue(messageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                reference.child("Message").child(othername).child(username).child(key).setValue(messageMap);
            }
        });
    }

}