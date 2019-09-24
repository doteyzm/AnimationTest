package com.test.networktest;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonCallbackListener<T> implements CallbackListener {
    public Class<T> responseClass;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private IJsonDataListener mIJsonDataListener;

    public JsonCallbackListener(Class<T> responseClass, IJsonDataListener mIJsonDataListener) {
        this.responseClass = responseClass;
        this.mIJsonDataListener = mIJsonDataListener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        //将得到的流转换成responseClass
        String response = getContent(inputStream);
        final T clazz = JSON.parseObject(response, responseClass);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mIJsonDataListener.onSuccess(clazz);
            }
        });
    }

    @Override
    public void onFailure() {

    }

    private String getContent(InputStream inputStream) {
        String content = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("test", e.toString());
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("test", e.toString());
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("test", e.toString());
        }
        return content;

    }
}
