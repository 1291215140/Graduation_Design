package com.example.main.data;

import lombok.Data;

@Data
public class ResponseData {
    private String message;
    private String status;

    public ResponseData() {
    }
    public ResponseData(String status) {
        this.status = status;
    }
}
