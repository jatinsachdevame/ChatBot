package com.example.chatbot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.CustomViewHolder> {

    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textMessage);
        }
    }
    List<ResponseMessage> responseMessageList;

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    public MessageAdapter(List<ResponseMessage> responseMessageList) {
        this.responseMessageList = responseMessageList;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.textView.setText(responseMessageList.get(position).getMessage());
    }

    @Override
    public int getItemViewType(int position) {
        if (responseMessageList.get(position).isMe()) {
            return R.layout.user_message;
        }
        return R.layout.bot_message;
    }

    @Override
    public int getItemCount() {
        return responseMessageList.size();
    }
}
