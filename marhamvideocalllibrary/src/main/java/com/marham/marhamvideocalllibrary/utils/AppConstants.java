package com.marham.marhamvideocalllibrary.utils;

public class AppConstants {

    public static final String TAG = "Marham";
    public static boolean isCallAlreadyInProgress = false;

    public static class HOSPITAL_TYPE {
        public static final String IN_CLINIC = "1";
        public static final String VIDEO_CONSULTATION = "2";
    }

    public static class API {

        public static class API_CALL_STATUS {
            public static final String SUCCESS_ACTION_BASED_APIS = "Success!";
            public static final String FAILED_ACTION_BASED_APIS = "Failed";

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
            public static final String PATIENT_ID_KEY = "patientId";
            public static final String DOCTOR_ID_KEY_2 = "doctorId";
            public static final String APPOINTMENT_ID_KEY = "appointmentId";
            public static final String USER_ID_KEY = "userId";
            public static final String REPORTS_KEY = "reports";
            public static final String PROGRAM_ID_KEY = "programId";
            public static final String HOSPITAL_TYPE = "hospitalType";
            public static final String APPLICATION_TYPE_KEY = "appType";
            public static final String ONLINE_CONSULATATION_ID_KEY = "onlineConsultationId";
            public static final String NOTIFICATION_TYPE_KEY = "notificationType";
            public static final String SEND_TO_PATIENT_KEY = "sendToPatient";
            public static final String SEND_TO_DOCTOR_KEY = "sendToDoctor";
            public static final String STATUS_KEY = "status";

        }

        public static class API_END_POINT_NUMBER {
            public static final int GET_DASHBOARD_DOCTORS = 1;
            public static final int GET_DASHBOARD_TOP_DISEASES = 2;
            public static final int GET_ALL_SPECIALITIES = 3;
            public static final int GET_ALL_DISEASES = 4;
            public static final int GET_VC_DOCTORS_LISTING = 5;
            public static final int GET_DOCTORS_DETAILS = 6;
            public static final int GET_HOSPITAL_AVAILABLE_DAYS_AND_DATES = 7;
            public static final int BOOK_ONLINE_CONSULTATION = 8;
            public static final int GET_USER_DETAILS = 9;
            public static final int GET_PATIENT_PRESCRIPTIONS = 10;
            public static final int GET_USER_APPOINTMENT = 11;
            public static final int GET_ONLINE_CONSULTATION_TOKEN = 12;
            public static final int UPDATE_DEVICE_TOKEN = 13;
            public static final int SEND_ONLINE_CONSULTATION_NOTIFICATION_SIGNAL = 14;
            public static final int UPDATE_LAST_SEEN_OF_USER = 15;
            public static final int UPDATE_APPOINTMENT_SUB_STATUS = 16;
        }

        public static class API_ERROR_MESSAGE {
            public static final String NETWORK_ERROR_MESSAGE = "Network not available! Please check if your device is connected to the Internet and try again.";
            public static final String JSON_PARSING_MESSAGE = "Unable to comprehend response from server";
        }

        public static class DEVICE_TYPE {
            public static final String ANDROID = "1";
        }

        public static class LANGUAGE {
            public static final String ENGLISH = "en";
        }

        public static class APPLICATION_TYPE {
            public static final String TELENOR = "1";
        }
    }

    public static class PROGRAM_ID {
        public static final String ONLINE_CONSULTATION = "4";
    }

    public static class PERMISSIONS {
        public static final String CAMERA_REQUEST = "1";
        public static final String PERMISSION_ALLOWED = "1";
        public static final String PERMISSION_NOT_ALLOWED = "0";

        public static class PermissionTypes {
            public static final int MICRO_PHONE = 3;
            public static final int CAMERA_AND_GALLERY_PERMISSION = 7;
            public static final int CALL_GSM = 8;
        }

    }

    public static class NOTIFICATIONS {

        public static class PUSH_NOTIFICATIONS {
            public static final String DOCTOR_REQUESTED_CALL = "1001";
            public static final String DOCTOR_CANCELLED_CALL = "1002";
            public static final String DOCTOR_ENDED_CALL = "1003";
            public static final String DOCTOR_ACCEPTED_CALL = "1011";
            public static final String DOCTOR_REJECTED_CALL = "1012";

            public static final String PATIENT_ACCPETED_CALL = "1004";
            public static final String PATIENT_REJECTED_CALL = "1005";
            public static final String PATIENT_REQUEST_CALL = "1009";
            public static final String PATIENT_CANCELED_CALL = "1010";
            public static final String PATIENT_ENDED_CALL = "1019";

            public static final String RECONNECTING_CALL = "1006";
            public static final String MISSED_CALL = "1008";
        }

        public static class LOCAL_NOTIFICATIONS {
            public static final String UPDATE_UNSEEN_NOTIFICATIONS_COUNTER = "update unseen notification counter";
            public static final String DOCTOR_REQUESTED_CALL = "1001";
            public static final String DOCTOR_CANCELLED_CALL = "1002";
            public static final String DOCTOR_ENDED_CALL = "1003";
            public static final String DOCTOR_ACCEPTED_CALL = "1011";
            public static final String DOCTOR_REJECTED_CALL = "1012";

        }


    }

    public static class OCAlertBoxKeys {
        public static final int VIDEO = 0;
        public static final int MIC = 1;
        public static final int END_CALL = 2;
    }

}
