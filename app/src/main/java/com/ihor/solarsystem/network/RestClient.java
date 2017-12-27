package com.ihor.solarsystem.network;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihor.solarsystem.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

class RestClient {

    private static final ObjectMapper sMapper = createObjectMapper();

    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create(sMapper))
            .baseUrl(NetworkContract.Base.ENDPOINT)
            .client(createHttpClient())
            .build();

    private RestClient() {
    }

    static Retrofit getInstance() {
        return RETROFIT;
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);
        return objectMapper;
    }

    private static OkHttpClient createHttpClient() {
        OkHttpClient.Builder okHttBuilder = new OkHttpClient.Builder();
        okHttBuilder.connectTimeout(NetworkContract.Base.TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(NetworkContract.Base.TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(NetworkContract.Base.TIMEOUT, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttBuilder.addInterceptor(interceptor);
        }
        return okHttBuilder.build();
    }
}
