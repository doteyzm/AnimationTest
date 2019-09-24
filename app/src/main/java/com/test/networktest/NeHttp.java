package com.test.networktest;

public class NeHttp {
    public static <T, M> void sendJsonRequest(String url, T t, Class<M> response, IJsonDataListener listener) {
        IHttpRequest httpRequest = new JsonHttpRequest();
        CallbackListener callbackListener = new JsonCallbackListener<>(response, listener);
        HttpTask ht = new HttpTask(url, t, httpRequest, callbackListener);
        ThreadPoolManager.getInstance().addTask(ht);

    }
}
