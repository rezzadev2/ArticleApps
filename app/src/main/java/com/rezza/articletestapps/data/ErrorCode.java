package com.rezza.articletestapps.data;

public enum ErrorCode {
    TO_MANY_REQUEST(429,"Too Many Requests. Developer accounts are limited to 100 requests over a 24 hour period"),
    REQUIRED_UPGRADE(426,"Developer accounts are limited to a max of 100 results. Please upgrade to a paid plan if you need more results"),
    UNDEFINED(-1,"Undefined Error. Please cek API"),
    INTERNET_PROBLEM(-2,"Connection Problem, Please check your internet connection"),
    SUCCESS(200,"ok"),
    NOT_FOUND(404,"API not found");

    final int code;
    final String message;
    ErrorCode(int code, String message){
        this.code  = code;
        this.message  = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ErrorCode map(int code){
        for (ErrorCode error : values()){
            if (error.code == code){
                return error;
            }
        }
        return UNDEFINED;
    }
}
