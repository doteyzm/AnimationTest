package com.test.networktest;

import java.io.InputStream;

public interface CallbackListener {
    void onSuccess(InputStream inputStream);

    void onFailure();
}
