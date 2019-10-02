package com.digital.code.globalexceptionhandling.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlobalException {
    public String status;
    public Integer code;
    public String message;
    public String url;
    public List<String> errors;
}
