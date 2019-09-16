package com.docsapp.chatbot.repository;

import androidx.lifecycle.MutableLiveData;

import com.docsapp.chatbot.database.DBManager;
import com.docsapp.chatbot.model.ChatBotMessage;
import com.docsapp.chatbot.network.ChatBotService;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatBotRepository {
    private ChatBotService chatBotService;
    private static ChatBotRepository chatBotRepository;

    private ChatBotRepository() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(loggingInterceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ChatBotService.HTTPS_API_CHATBOT_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        chatBotService = retrofit.create(ChatBotService.class);

    }

    public synchronized static ChatBotRepository getInstance() {
        if (chatBotRepository == null) {
            if (chatBotRepository == null) {
                chatBotRepository = new ChatBotRepository();
            }
        }
        return chatBotRepository;
    }

    public MutableLiveData<List<ChatBotMessage>> getChatBotList(String userId,
                                                                String chatBotId,
                                                                String externalId,
                                                                String message) {
        final MutableLiveData<List<ChatBotMessage>> data = new MutableLiveData<>();

        if(DBManager.getInstance().getChatBotMessageList() != null &&
                !DBManager.getInstance().getChatBotMessageList().isEmpty()) {
            data.setValue(DBManager.getInstance().getChatBotMessageList());
        } else {

        chatBotService.getChatBotMessage(userId, chatBotId, externalId, message)
                .enqueue(new Callback<List<ChatBotMessage>>() {
                    @Override
                    public void onResponse(Call<List<ChatBotMessage>> call, Response<List<ChatBotMessage>> response) {
                        handleDoOnSuccess(response.body());
                        data.setValue(getChatBotMessageList(response.body()));

                    }

                    @Override
                    public void onFailure(Call<List<ChatBotMessage>> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        }

        return data;
    }

    public void handleDoOnSuccess(List<ChatBotMessage> chatBotMessageList) {
        if (null != chatBotMessageList) {
            if (null != DBManager.getInstance()) {
                DBManager.getInstance().insertObjects(chatBotMessageList);
            }
        }


    }

    private List<ChatBotMessage> getChatBotMessageList(List<ChatBotMessage> chatBotMessageList) {
        if (DBManager.getInstance() != null && DBManager.getInstance().getDBInstance() != null) {
            chatBotMessageList = DBManager.getInstance().getDBInstance().copyFromRealm(DBManager.getInstance().getChatBotMessageList());
        }
        return chatBotMessageList;
    }
}