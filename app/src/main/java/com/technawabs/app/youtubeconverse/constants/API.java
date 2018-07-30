package com.technawabs.app.youtubeconverse.constants;

import java.io.Serializable;

public enum API implements Serializable{

    BASE(""),
    HOME("");

    private String url;

    API(String url){
        this.url=url;
    }

    public String getURL(){
        return BASE.getURL()+url;
    }

}
