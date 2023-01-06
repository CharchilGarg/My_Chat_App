package com.example.mychatapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private RecyclerView rv;
    private EditText message;
    private Button send;
    FirebaseDatabase database;
    DatabaseReference reference;
    String username;
    String othername;
    MessageAdapter adapter;
    List<ModleClass> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        imageView = findViewById(R.id.BackButton);
        textView = findViewById(R.id.otherNameTextView);
        rv = findViewById(R.id.rv);
        message = findViewById(R.id.message);
        //flo = findViewById(R.id.sendButton);
        send = findViewById(R.id.sendbutton);

        rv.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        username = getIntent().getStringExtra("username");
        othername = getIntent().getStringExtra("othername");

        textView.setText(othername);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MessageActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        /*flo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usermessage = message.getText().toString();

                if(!usermessage.equals(""))
                {
                    message.setText("");
                    sendMessage(usermessage);
                }
            }
        });*/

        send.setOnClickListener(new View.OnClickListener() {
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

        getMessage();

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

    public void getMessage()
    {
        reference.child("Message").child(username).child(othername).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ModleClass modleClass = snapshot.getValue(ModleClass.class);
                list.add(modleClass);
                adapter.notifyDataSetChanged();
                rv.scrollToPosition(list.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new MessageAdapter(list,username);
        rv.setAdapter(adapter);
    }
}