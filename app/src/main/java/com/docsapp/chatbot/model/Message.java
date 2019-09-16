
package com.docsapp.chatbot.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

public class Message extends RealmObject {

    @SerializedName("chatBotName")
    @Expose
    private String chatBotName;
    @SerializedName("chatBotID")
    @Expose
    private Integer chatBotID;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("emotion")
    @Expose
    private String emotion;

    public String getChatBotName() {
        return chatBotName;
    }

    public void setChatBotName(String chatBotName) {
        this.chatBotName = chatBotName;
    }

    public Integer getChatBotID() {
        return chatBotID;
    }

    public void setChatBotID(Integer chatBotID) {
        this.chatBotID = chatBotID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

}
