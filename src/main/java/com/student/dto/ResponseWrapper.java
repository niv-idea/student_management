package com.student.dto;

import com.student.enums.Status;
import lombok.Data;

@Data
public class ResponseWrapper {
    private Status status;
    private Object data;
    public ResponseWrapper(Status status, Object data) {
        this.status = status;
        this.data = data; //this is super class object which we can take any type of datatype
    }
}
