package com.zhou.demo.util;

import okhttp3.*;

import java.io.IOException;

public class HttpUtils {

    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();

    /**
     * 发送GET请求
     */
    public static String sendGetRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            assert response.body() != null;
            return response.body().string();
        }
    }

    /**
     * 发送POST请求（发送JSON数据）
     */
    @SuppressWarnings("Duplicates")
    public static String sendPostJsonRequest(String url, String jsonBody) throws IOException {
        MediaType header = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(header, jsonBody);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            assert response.body() != null;
            return response.body().string();
        }
    }

    /**
     * 发送POST请求（发送表单数据）
     */
    @SuppressWarnings("Duplicates")

    public static String sendPostFormRequest(String url, FormBody.Builder formBodyBuilder) throws IOException {
        RequestBody requestBody = formBodyBuilder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            assert response.body() != null;
            return response.body().string();
        }
    }

    public static void main(String[] args) {
        // 使用示例
        try {
            // GET请求
            String responseGet = sendGetRequest("https://jsonplaceholder.typicode.com/posts/1");
            System.out.println("GET Response:");
            System.out.println(responseGet);

            // POST请求发送JSON数据
            String jsonBody = "{\"title\": \"foo\", \"body\": \"bar\", \"userId\": 1}";
            String responsePostJson = sendPostJsonRequest("https://jsonplaceholder.typicode.com/posts", jsonBody);
            System.out.println("POST JSON Response:");
            System.out.println(responsePostJson);

            // POST请求发送表单数据
            FormBody.Builder formBodyBuilder = new FormBody.Builder()
                    .add("username", "user123")
                    .add("password", "password123");
            String responsePostForm = sendPostFormRequest("https://example.com/login", formBodyBuilder);
            System.out.println("POST Form Response:");
            System.out.println(responsePostForm);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
