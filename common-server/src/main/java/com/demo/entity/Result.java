package com.demo.entity;

import lombok.Data;

@Data
public class Result<T> {
    private T data;

    private String msg;

    private int result;

    private boolean success;
}