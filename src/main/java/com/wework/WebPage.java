package com.wework;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class WebPage {

    private String url;
    private Integer rank;
    private Integer sessions=0;
    private Map<String, String> searchTermExists = new HashMap<String, String>();
    private String executedBy;
    private String msg;

    public WebPage(Integer rank, String url){
        this.rank = rank;
        this.url = url;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getSessions() {
        return sessions;
    }

    public void setSessions(Integer sessions) {
        this.sessions = sessions;
    }

    public void incrementSession(){
        this.sessions++;
    }

    public Map<String, String> getSearchTermMap() {
        return searchTermExists;
    }

    public void setSearchTermMap(Map<String, String> searchTermExists) {
        this.searchTermExists = searchTermExists;
    }

    public String toString(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        String json = gson.toJson(this);
        return json + ",";
    }

    public String getExecutedBy() {
        return executedBy;
    }

    public void setExecutedBy(String executedBy) {
        this.executedBy = executedBy;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
