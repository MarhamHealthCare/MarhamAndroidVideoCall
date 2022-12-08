package com.marham.marhamvideocalllibrary.model;

import androidx.annotation.Keep;

import java.util.List;

import okhttp3.internal.http2.Header;

@Keep
public class ServerResponse {


    private List<Header> headerList;
    private int requestCode;
    private String return_status;
    private String message;

    public void setHeaderList(List<Header> headerList) {
        this.headerList = headerList;
    }

    public void setRequestCode(int code) {
        this.requestCode = code;
    }

    public void setReturn_status(String return_status) {
        this.return_status = return_status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Header> getHeaderList() {
        return headerList;
    }

    public int getRequestCode() {
        return requestCode;
    }


    public String getReturn_status() {
        return return_status;
    }

    public String getMessage() {
        return message;
    }

}
