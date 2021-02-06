package com.cyansoft.maodou_manger.Service;


import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/**
 * @author 曹晓东 ,Created on 2020/5/1
 * 联系： 2806029294@qq.com
 */
public class HttpUtils {

    public static String okPost(String url, String json) throws IOException {
        OkHttpClient client=new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }


    public static String okGet(String url) throws IOException {
        OkHttpClient client=new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();

    }
}




