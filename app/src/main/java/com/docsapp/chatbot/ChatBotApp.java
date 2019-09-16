package com.docsapp.chatbot;

import android.app.Application;

import com.docsapp.chatbot.database.DBManager;

public class ChatBotApp extends Application {

    private static ChatBotApp instance;

    public static ChatBotApp getInstance() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        DBManager.initDB(this);
    }
}