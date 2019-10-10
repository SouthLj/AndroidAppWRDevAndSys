package com.example.sc60devicesutils;

public class Sc60I2c {
    int fd;
    private final static String TAG = "Sc60I2c";
   // private String spiName;
    public Sc60I2c(){

    }
    public int open(String i2cName){
        fd = open_native(i2cName);
        return fd > 0 ? fd :-1;
    }

    private native int open_native(String i2cName);
}
