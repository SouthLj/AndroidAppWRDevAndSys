package com.example.sc60devicesutils;

public class Sc60Pwm {
    int fd;
    private final static String TAG = "Sc60Pwm";
    public Sc60Pwm(){

    }
    public int open(String pwmName){
        fd = open_native(pwmName);
        return fd > 0 ? fd :-1;
    }

    private native int open_native(String pwmName);
}
