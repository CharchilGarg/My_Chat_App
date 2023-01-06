package com.example.mychatapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{

    List<ModleClass> list;
    String userName;
    boolean status;
    int send;
    int recive;

    public MessageAdapter(List<ModleClass> list, String userName) {
        this.list = list;
        this.userName = userName;

        status = false;
        send = 1;
        recive = 2;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        if(viewType == send)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.send_card,parent,false);
        }
        else
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recive_card,parent,false);
        }

        return new MessageViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            if(status)
            {
                textView = itemView.findViewById(R.id.sendbutton);
            }
            else
            {
                textView = itemView.findViewById(R.id.reccive);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {

        if(list.get(position).getFrom().equals(userName))
        {
            status = true;
            return send;
        }
        else
        {
            status = false;
            return recive;
        }
    }
}
