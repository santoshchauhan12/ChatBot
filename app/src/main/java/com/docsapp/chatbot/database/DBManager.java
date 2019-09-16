package com.docsapp.chatbot.database;

import android.content.Context;
import android.util.Log;

import com.docsapp.chatbot.constants.ChatBotConstants;
import com.docsapp.chatbot.model.ChatBotMessage;

import java.util.List;
import java.util.logging.Logger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.exceptions.RealmException;

public class DBManager {
    public static final String TAG = DBManager.class.getSimpleName();
    private static DBManager instance;

    public static synchronized DBManager getInstance() {
        if (null == instance) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() {

    }

    public static boolean initDB(Context context) {
        if (null != context) {
            Realm.init(context);
            RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                    .schemaVersion(ChatBotConstants.REALM.DB_VERSION)
                    .migration(new ChatBotMigration())
                    .name(ChatBotConstants.REALM.DB_NAME).build();

            Realm.setDefaultConfiguration(realmConfiguration);
            return true;
        }
        return false;
    }

    public  synchronized Realm getDBInstance() {
        return Realm.getDefaultInstance();
    }

    public boolean insertObject(final RealmObject realmObject) {
        boolean stat = false;
        Log.d(TAG,"insertObject:" + realmObject);
        Realm realm = getDBInstance();
        synchronized (realm) {
            if (null != realm && null != realmObject) {
                try {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(realmObject);
                    realm.commitTransaction();
                    stat = true;
                }catch (RealmException e) {
                    Logger.getLogger(TAG,"insertObject:" + e);
                }


            }
        }
        return stat;
    }


    public boolean asyncInsertObject(final RealmObject realmObject) {
        boolean stat = false;
        Log.d(TAG,"asyncInsertObject:" + realmObject);
        Realm realm = getDBInstance();
        if (null != realm && null != realmObject) {
            try {
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(realmObject);
                realm.commitTransaction();
                stat = true;
            }catch (RealmException e) {
                Logger.getLogger(TAG,"asyncInsertObject:" + e);
            }

        }
        return stat;
    }


    public boolean insertObjects(final List<ChatBotMessage> list) {
        boolean stat = false;
        Realm realm = getDBInstance();
        Log.d(TAG,"insertObjects List:" + list.size());
        if (null != realm && null != list && !list.isEmpty()) {
            realm.beginTransaction();
            realm.insertOrUpdate(list);
            realm.commitTransaction();
            realm.close();
            stat = true;
        }
        return stat;
    }


    public void clearDb() {
        Log.d(TAG,"clearDb");
        Realm realm = getDBInstance();
        if (null != realm) {
            realm.beginTransaction();
            realm.deleteAll();
            realm.commitTransaction();
            realm.close();
        }
    }

    public List<ChatBotMessage> getChatBotMessageList() {
        List<ChatBotMessage> chatBotMessageList = null;
        Log.d(TAG,"getChatBotMessageList");
        if (getDBInstance() != null) {
            chatBotMessageList = getDBInstance().where(ChatBotMessage.class).findAll();
            getDBInstance().close();
        }
        return chatBotMessageList;

    }
}
