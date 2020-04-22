package com.btkbootcamp.mission3;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RestErrorResponse {

    private int status;

    public RestErrorResponse(){

    }

    private String message;
    private long timestamp;

    public RestErrorResponse(int status, String message, long timestamp){
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }
}
