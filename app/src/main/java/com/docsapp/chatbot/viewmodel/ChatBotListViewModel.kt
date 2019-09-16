package com.docsapp.chatbot.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.docsapp.chatbot.model.ChatBotMessage
import com.docsapp.chatbot.network.RequestData.ChatBotMessageRequest
import com.docsapp.chatbot.repository.ChatBotRepository


class ChatBotListViewModel(application: Application,
                           chatBotMessageRequest: ChatBotMessageRequest
) : AndroidViewModel(application) {

    private var projectListObservable: MutableLiveData<List<ChatBotMessage>>? = null

    init {

        projectListObservable = ChatBotRepository.getInstance().getChatBotList(chatBotMessageRequest.userId,
            chatBotMessageRequest.chatBotId,
            chatBotMessageRequest.externalId,
            chatBotMessageRequest.message)
    }

    fun getChatBotListObservable(): MutableLiveData<List<ChatBotMessage>>? {

        return projectListObservable
    }

    class Factory(private val application: Application,
                  private val chatBotMessageRequest: ChatBotMessageRequest
    ): ViewModelProvider.Factory {


        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ChatBotListViewModel(application, chatBotMessageRequest) as T
        }
    }

}