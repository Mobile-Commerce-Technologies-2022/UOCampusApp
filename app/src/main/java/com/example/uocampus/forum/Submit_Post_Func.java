package com.example.uocampus.forum;

public class Submit_Post_Func {
    private String Time;
    private String HostID;
    private String post_content;
    private String title;
    public Submit_Post_Func(String ID, String time, String title, String content){
        this.HostID = ID;
        this.Time = time;
        this.title = title;
        this.post_content = content;
    }
    public Submit_Post_Func(){}
    public String getTime() {
        return Time;
    }
    public String getTitle() {
        return title;
    }
    public void setTime(String time) {
        Time = time;
    }
    public String getHostID() {
        return HostID;
    }
    public void setHostID(String hostID) {
        HostID = hostID;
    }
    public String getPost_content() {
        return post_content;
    }
    public void setPost_content(String mom_content) {
        this.post_content = mom_content;
    }
    public void setTitle(String title){this.title = title;}
}
