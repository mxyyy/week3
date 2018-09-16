package com.bwie.week3.model;



import com.bwie.week3.utils.Okhttputils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by _ヽ陌路离殇ゞ on 2018/9/15.
 */

public class IModelImp implements IModel {
    @Override
    public void getRequest(String urlString, final CallBack callBack) {
        Okhttputils.getInstance().get(urlString, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                callBack.success(string);
            }
        });
    }
}
