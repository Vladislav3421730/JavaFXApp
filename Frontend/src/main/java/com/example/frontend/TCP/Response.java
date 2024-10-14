package com.example.frontend.TCP;

import com.example.frontend.TCP.enums.ResponseType;

public class Response {
    private String message;
    private ResponseType responseType;


    public Response(String message,ResponseType requestType) {
        this.message = message;
        this.responseType = requestType;

    }

    public Response() {
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType requestType) {
        this.responseType = requestType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
