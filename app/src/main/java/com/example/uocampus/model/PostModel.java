package com.example.uocampus.model;

import androidx.annotation.NonNull;

public class PostModel {
    private String Time;
    private String username;
    private String post_content;
    private String title;
    public PostModel(String username, String time, String title, String content){
        this.username = username;
        this.Time = time;
        this.title = title;
        this.post_content = content;
    }
    public PostModel(){}
    public String getTime() {
        return Time;
    }
    public String getTitle() {
        return title;
    }
    public void setTime(String time) {
        Time = time;
    }
    public String getUsername() { return username; }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPost_content() {
        return post_content;
    }
    public void setPost_content(String mom_content) {
        this.post_content = mom_content;
    }
    public void setTitle(String title){this.title = title;}

    @NonNull
    @Override
    public String toString() {
        return "PostModel{" +
                "Time='" + Time + '\'' +
                ", HostID='" + username + '\'' +
                ", post_content='" + post_content + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
