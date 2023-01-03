package com.marham.marhamvideocalllibrary.model.notification;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

@Keep
public class ServerResponsePushNotification implements Parcelable {

    public static String BUNDLE_KEY = "ServerResponsePushNotification";

    private String heading;
    private String message;
    private String activityID;
    private String url;
    private String dataPushNotification;
    private String notificationId;
    private String notificationCreatedDate;
    private String seen;
    private String notification_type;
    private String unseenCount;
    private String customParams;


    protected ServerResponsePushNotification(Parcel in) {
        heading = in.readString();
        message = in.readString();
        activityID = in.readString();
        url = in.readString();
        dataPushNotification = in.readString();
        notificationId = in.readString();
        notificationCreatedDate = in.readString();
        seen = in.readString();
        notification_type = in.readString();
        unseenCount = in.readString();
        customParams = in.readString();
    }

    public static final Parcelable.Creator<ServerResponsePushNotification> CREATOR = new Parcelable.Creator<ServerResponsePushNotification>() {
        @Override
        public ServerResponsePushNotification createFromParcel(Parcel in) {
            return new ServerResponsePushNotification(in);
        }

        @Override
        public ServerResponsePushNotification[] newArray(int size) {
            return new ServerResponsePushNotification[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(heading);
        parcel.writeString(message);
        parcel.writeString(activityID);
        parcel.writeString(url);
        parcel.writeString(dataPushNotification);
        parcel.writeString(notificationId);
        parcel.writeString(notificationCreatedDate);
        parcel.writeString(seen);
        parcel.writeString(notification_type);
        parcel.writeString(unseenCount);
        parcel.writeString(customParams);
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getActivityID() {
        return activityID;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDataPushNotification() {
        return dataPushNotification;
    }

    public void setDataPushNotification(String dataPushNotification) {
        this.dataPushNotification = dataPushNotification;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationCreatedDate() {
        return notificationCreatedDate;
    }

    public void setNotificationCreatedDate(String notificationCreatedDate) {
        this.notificationCreatedDate = notificationCreatedDate;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(String notification_type) {
        this.notification_type = notification_type;
    }

    public String getUnseenCount() {
        return unseenCount;
    }

    public void setUnseenCount(String unseenCount) {
        this.unseenCount = unseenCount;
    }

    public String getCustomParams() {
        return customParams;
    }

    public void setCustomParams(String customParams) {
        this.customParams = customParams;
    }

}
