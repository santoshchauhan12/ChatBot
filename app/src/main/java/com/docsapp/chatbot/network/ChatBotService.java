package com.docsapp.chatbot.network;

import com.docsapp.chatbot.model.ChatBotMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ChatBotService {
    String HTTPS_API_CHATBOT_URL = "https://www.personalityforge.com";


    @GET("/api/chat/")
    Call<List<ChatBotMessage>> getChatBotMessage(@Query("apikey") String apiKey,
                                                 @Query("chatBotID") String chatbotId,
                                                 @Query("externalId") String externalId,
                                                 @Query("message") String message);
}