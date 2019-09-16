package com.docsapp.chatbot.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.docsapp.chatbot.R;
import com.docsapp.chatbot.constants.ChatBotConstants;
import com.docsapp.chatbot.model.ChatBotMessage;
import com.docsapp.chatbot.model.Message;
import com.docsapp.chatbot.network.RequestData.ChatBotMessageRequest;
import com.docsapp.chatbot.view.adapter.ChatBotMessageListAdapter;
import com.docsapp.chatbot.viewmodel.ChatBotListViewModel;
import com.docsapp.chatbot.viewmodel.QUViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class ChatListFragment extends Fragment {

    private View rootView;

    private RecyclerView chatRecyclerView;
    private Button chatButton;
    private EditText msgEditText;

    private List<ChatBotMessage> chatBotMessageList = new ArrayList<>();
    private ChatBotMessageListAdapter chatMessageAdapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_chat_list, container, false);

        setUpUI();
        return rootView;
    }

    private void setUpUI() {
        chatRecyclerView = rootView.findViewById(R.id.chat_rv);
        chatButton = rootView.findViewById(R.id.send_button);
        msgEditText = rootView.findViewById(R.id.message_editText);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        chatRecyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        chatMessageAdapter = new ChatBotMessageListAdapter(getActivity(), chatBotMessageList);
        chatRecyclerView.setAdapter(chatMessageAdapter); // set the Adapter to RecyclerView
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ChatBotMessageRequest chatBotData = new ChatBotMessageRequest();
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatBotData.setUserId(ChatBotConstants.APICONSTANS.API_KEY);
                chatBotData.setChatBotId(ChatBotConstants.APICONSTANS.CHATBOT_ID);
                chatBotData.setExternalId("docsApp");
                chatBotData.setMessage(msgEditText.getText().toString());

                ChatBotListViewModel.Factory factory = new ChatBotListViewModel.Factory(
                        getActivity().getApplication(), chatBotData);

                ChatBotListViewModel viewModel = ViewModelProviders.of(ChatListFragment.this,
                        factory).get(ChatBotListViewModel.class);

                observeViewModel(viewModel);
            }
        });
    }

    private void observeViewModel(final ChatBotListViewModel viewModel) {
        viewModel.getChatBotListObservable().observe(this, new Observer<List<ChatBotMessage>>() {
            @Override
            public void onChanged(@Nullable List<ChatBotMessage> messageList) {
                if (messageList != null  && messageList.size() > 0) {
                    chatBotMessageList = messageList;
                    chatMessageAdapter.notifyDataSetChanged();
                    msgEditText.setText("");
                }

            }
        });
    }
}