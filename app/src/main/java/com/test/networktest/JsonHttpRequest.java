package com.test.networktest;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonHttpRequest implements IHttpRequest {
    private String url;
    private byte[] data;
    private CallbackListener callbackListener;
    private HttpURLConnection httpURLConnection;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public void setListener(CallbackListener callBackListener) {
        this.callbackListener = callBackListener;
    }

    @Override
    public void execute() {
        URL url = null;
        try {
            url = new URL(this.url);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(6000);//设置连接超时时间
            httpURLConnection.setUseCaches(false);//不使用缓存
            httpURLConnection.setInstanceFollowRedirects(true);//设置这个连接是否可以被重定向（成员函数，仅作用与当前函数）
            httpURLConnection.setReadTimeout(3000);//设置响应超时时间
            httpURLConnection.setDoInput(true);//设置这个链接是否可以写入数据
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");//设置请求的方式
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");//设置消息的类型
            httpURLConnection.connect();
            //使用字节流发送数据
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(outputStream);
            bos.write(data);//把字节数组的数据写入缓冲区
            bos.flush();//刷新缓存区，发送数据
            outputStream.close();
            bos.close();
            //字节流写入数据
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = httpURLConnection.getInputStream();
                callbackListener.onSuccess(inputStream);
            } else {
                throw new RuntimeException("请求失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("请求失败");
        } finally {
            httpURLConnection.disconnect();
        }


    }
}
