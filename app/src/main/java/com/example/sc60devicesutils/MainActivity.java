package com.example.sc60devicesutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";
    public final static String SPIDEV = "/dev/spidev6.0";
    public final static String I2CDEV = "/dev/i2c-2";
    public final static String PWMDEV = "/dev/pwm-0";
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int ret;

        Sc60I2c i2c = new Sc60I2c();
        ret = i2c.open(I2CDEV);
        if(ret == -1)
            Log.e(TAG,"open i2cdev failed ："+ I2CDEV);
        else
            Log.e(TAG,"i2c success,fd ："+ ret);

        Sc60Spi spi = new Sc60Spi();
        ret = spi.open(SPIDEV);
        if(ret == -1)
            Log.e(TAG,"open spidev failed ："+ SPIDEV);
        else
            Log.e(TAG,"spi success,fd ："+ ret);


        Sc60Pwm pwm = new Sc60Pwm();
        ret = pwm.open(PWMDEV);
        if(ret == -1)
            Log.e(TAG,"open pwm failed ："+ PWMDEV);
        else
            Log.e(TAG,"pwm success,fd ："+ ret);

        execShell("echo 80 > /sys/devices/soc/soc:qcom,gpioclk/gpio_clock_duty");
    }


    public String execShell(String... cmds){
        StringBuilder stringBuilder = new StringBuilder();
        Process process = null;
        BufferedReader reader = null;
        BufferedReader error;
        OutputStream os;
        boolean res = true;
        try {
            process = Runtime.getRuntime().exec("sh");
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            os = process.getOutputStream();
            String line;

            for (String cmd:cmds) {
                Log.d(TAG, "execShell: " + cmd);
                os.write((cmd + "\n").getBytes());
                os.flush();
            }
            os.write(("exit\n").getBytes());
            os.flush();
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
                Log.d(TAG, "execShell return: "+line);
            }

            while ((line = error.readLine()) != null) {
                res =  false;
                Log.e(TAG, "execShell error: "+line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            res =  false;
        }finally {
            try {
                if (process != null)
                    process.waitFor();
                if (reader != null)
                    reader.close();
                if (process != null)
                    process.destroy();
            } catch (Exception e) {
                e.printStackTrace();
                res =  false;
            }
        }

        if (res){
            return stringBuilder.toString();
        }else{
            return "error";
        }

    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
