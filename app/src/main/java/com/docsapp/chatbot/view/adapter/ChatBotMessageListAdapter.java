package com.docsapp.chatbot.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.docsapp.chatbot.R;
import com.docsapp.chatbot.model.ChatBotMessage;

import java.util.List;

public class ChatBotMessageListAdapter extends RecyclerView.Adapter {

    List<ChatBotMessage> chatBotMessageArrayList;
    Context context;

    public ChatBotMessageListAdapter(Context context, List<ChatBotMessage> chatBotMessageArrayList) {
        this.context = context;
        this.chatBotMessageArrayList = chatBotMessageArrayList;
    }

    @Override
    public ChatBotMessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
// infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_chat_bot_message, parent, false);
// set the view's size, margins, paddings and layout parameters
        ChatBotMessageViewHolder vh = new ChatBotMessageViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((ChatBotMessageViewHolder) holder).bind(chatBotMessageArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return chatBotMessageArrayList.size();
    }

    public class ChatBotMessageViewHolder extends RecyclerView.ViewHolder {
        TextView receivedMsgTv;// init the item view's
        TextView sendMsgTv;// init the item view's

        public ChatBotMessageViewHolder(View itemView) {
            super(itemView);

// get the reference of item view's
            receivedMsgTv = (TextView) itemView.findViewById(R.id.msg_received_textview);
            sendMsgTv = (TextView) itemView.findViewById(R.id.msg_sent_textview);
        }

        public void bind(ChatBotMessage chatBotMessage) {
            receivedMsgTv.setText(chatBotMessage.getMessage().getMessage());
        }
    }
}