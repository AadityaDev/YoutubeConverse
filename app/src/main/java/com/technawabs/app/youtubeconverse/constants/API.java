package com.technawabs.app.youtubeconverse.constants;

import java.io.Serializable;

public enum API implements Serializable{

    BASE(""),
    HOME(""),
    USERS("https://converse-10066.firebaseio.com/users"),
    FIREBASE_USER("https://converse-10066.firebaseio.com/users.json"),
    FIREBASE_CHAT("https://converse-10066.firebaseio.com"),
    FIREBASE_MESSAGES("https://converse-10066.firebaseio.com/messages/");

    private String url;

    API(String url){
        this.url=url;
    }

    public String getURL(){
        return url;
    }

}
