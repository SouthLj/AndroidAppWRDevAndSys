package com.example.sc60devicesutils;

public class Sc60Spi {
    int fd;
    private final static String TAG = "Sc60spi";
    private String spiName;

    public Sc60Spi() {}
    public Sc60Spi(String spiName){
        this.spiName = spiName;
    }

    public int open(String spiName){
       fd = open_native(spiName);
       return fd > 0 ? fd :-1;
    }

    private native int open_native(String spiName);
}
