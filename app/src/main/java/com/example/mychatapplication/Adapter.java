package com.example.mychatapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {

    List<String> user;
    String Username;
    Context context;
    FirebaseDatabase database;
    DatabaseReference reference;

    public Adapter(List<String> user, String username, Context context) {
        this.user = user;
        Username = username;
        this.context = context;

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card,parent,false);

        return new viewHolder(view);
    }

    @Override
    public int getItemCount() {
        return user.size();
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        reference.child("Users").child(user.get(position)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String otherName = snapshot.child("UserName").getValue().toString();
                String imageUri = snapshot.child("image").getValue().toString();

                holder.username.setText(otherName);

                if(imageUri.equals("null"))
                {
                    holder.userimage.setImageResource(R.drawable.userprofile);
                }
                else
                {
                    Picasso.get().load(imageUri).into(holder.userimage);
                }

                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(context,MessageActivity.class);
                        i.putExtra("username",Username);
                        i.putExtra("othername",otherName);
                        context.startActivity(i);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public class viewHolder extends RecyclerView.ViewHolder{

        private TextView username;
        private CircleImageView userimage;
        private CardView cardView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.textViewUserName);
            userimage = itemView.findViewById(R.id.UsercircleImageView);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }
}
