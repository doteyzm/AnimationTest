package com.test.networktest;

public interface IHttpRequest {
    void setUrl(String url);

    void setData(byte[] data);

    void setListener(CallbackListener callBackListener);

    void execute();
}
