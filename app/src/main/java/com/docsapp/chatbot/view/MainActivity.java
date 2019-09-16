package com.docsapp.chatbot.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.docsapp.chatbot.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            ChatListFragment fragment = new ChatListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, ChatListFragment.class.getName()).commit();
        }
    }
}
