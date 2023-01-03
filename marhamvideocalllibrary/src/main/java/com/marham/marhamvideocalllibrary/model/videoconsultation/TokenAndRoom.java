package com.marham.marhamvideocalllibrary.model.videoconsultation;

import androidx.annotation.Keep;

@Keep
public class TokenAndRoom {

    private String current_time_miliseconds;

    private String room;

    private String token;

    public String getCurrent_time_miliseconds() {
        return current_time_miliseconds;
    }

    public void setCurrent_time_miliseconds(String current_time_miliseconds) {
        this.current_time_miliseconds = current_time_miliseconds;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
