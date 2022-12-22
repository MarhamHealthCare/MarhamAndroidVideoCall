package com.marham.marhamvideocalllibrary.model.user;

import androidx.annotation.Keep;

import com.marham.marhamvideocalllibrary.model.general.ServerResponse;

@Keep
public class MarhamUser  {

    private String FName;

    private String id;

    private String email;

    private String LName;

    private String avatar;

    private String phone;

    private String name;

    private String username;

    private String token;

    public String getFName ()
    {
        return FName;
    }

    public void setFName (String FName)
    {
        this.FName = FName;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getLName ()
    {
        return LName;
    }

    public void setLName (String LName)
    {
        this.LName = LName;
    }

    public String getAvatar ()
    {
        return avatar;
    }

    public void setAvatar (String avatar)
    {
        this.avatar = avatar;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getUsername ()
    {
        return username;
    }

    public void setUsername (String username)
    {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
