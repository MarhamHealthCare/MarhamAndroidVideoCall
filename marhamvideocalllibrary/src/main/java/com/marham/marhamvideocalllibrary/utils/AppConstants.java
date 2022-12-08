package com.marham.marhamvideocalllibrary.utils;

public class AppConstants {

    public static class API {

        public static class API_CALL_STATUS{
            public static final String SUCCESS = "Success!";
            public static final String FAILED = "Failed";
        }

        public static class API_KEYS {
            public static final String CITY = "city";
            public static final String TOP_ONLY = "top_only";
            public static final String NEW_ID = "new";
        }

        public static class API_END_POINT_NUMBER {
            public static final int GET_DASHBOARD_DOCTORS = 1;
            public static final int GET_DASHBOARD_TOP_DISEASES = 2;
            public static final int GET_ALL_SPECIALITIES = 3;
        }

        public static class API_ERROR_MESSAGE {
            public static final String NETWORK_ERROR_MESSAGE = "Network not available! Please check if your device is connected to the Internet and try again.";
            public static final String JSON_PARSING_MESSAGE = "Unable to comprehend response from server";
        }
    }
}
