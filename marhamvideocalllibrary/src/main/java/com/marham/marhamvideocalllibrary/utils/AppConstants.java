package com.marham.marhamvideocalllibrary.utils;

public class AppConstants {

    public static class HOSPITAL_TYPE{
        public static final String IN_CLINIC = "1";
        public static final String VIDEO_CONSULTATION = "2";
    }

    public static class API {

        public static class API_CALL_STATUS{
            public static final String SUCCESS_OLD = "Success!";
            public static final String FAILED_OLD = "Failed";

            public static final String SUCCESS = "true";
            public static final String FAILED = "false";
        }

        public static class API_KEYS {
            public static final String CITY = "city";
            public static final String TOP_ONLY = "topOnly";
            public static final String NEW_ID = "new";
            public static final String DOCTOR_ID_KEY = "dID";
            public static final String PAGE_KEY = "page";
            public static final String LOGGED_IN_USER_ID_KEY = "loggedInUserId";
            public static final String DEVICE_TOKEN_KEY = "devicetoken";
            public static final String DEVICE_TYPE_KEY = "deviceType";
            public static final String LANGUAGE_KEY = "language";
            public static final String USER_NAME = "user_name";
            public static final String USER_PHONE = "user_phone";
        }

        public static class API_END_POINT_NUMBER {
            public static final int GET_DASHBOARD_DOCTORS = 1;
            public static final int GET_DASHBOARD_TOP_DISEASES = 2;
            public static final int GET_ALL_SPECIALITIES = 3;
            public static final int GET_ALL_DISEASES = 4;
            public static final int GET_DOCTOR_LISTING_FILTERS = 5;
            public static final int GET_VC_DOCTORS_LISTING = 6;
            public static final int GET_DOCTORS_DETAILS = 7;
            public static final int GET_HOSPITAL_AVAILABLE_DAYS_AND_DATES = 8;
            public static final int BOOK_ONLINE_CONSULTATION = 9;
            public static final int GET_USER_DETAILS = 10;
        }

        public static class API_ERROR_MESSAGE {
            public static final String NETWORK_ERROR_MESSAGE = "Network not available! Please check if your device is connected to the Internet and try again.";
            public static final String JSON_PARSING_MESSAGE = "Unable to comprehend response from server";
        }

        public static class DEVICE_TYPE{
            public static final String ANDROID = "1";
        }

        public static class LANGUAGE{
            public static final String ENGLISH = "en";
        }
    }

    public static class PROGRAM_ID{
        public static final String ONLINE_CONSULTATION = "4";
    }

    public static class PERMISSIONS{
        public static final String CAMERA_REQUEST = "1";
    }
}
