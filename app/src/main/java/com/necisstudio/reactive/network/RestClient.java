package com.necisstudio.reactive.network;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.necisstudio.reactive.convert.JacksonConvert;
import com.necisstudio.reactive.items.ProfileGSON;
import com.necisstudio.reactive.items.ProfileJackson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit.converter.JacksonConverter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;


public class RestClient {

    private static ApiInterface ApiInterface;

    public static ApiInterface getClient() {
        if (ApiInterface == null) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setPropertyNamingStrategy(new PropertyNamingStrategy
                    .PascalCaseStrategy());

            OkHttpClient okclient = new OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(50,TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request().newBuilder()
                                    .addHeader("Accept", "Application/JSON").build();
                            return chain.proceed(request);
                        }
                    })
                    .build();

            Retrofit client = new Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .client(okclient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    //.addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(new JacksonConvert(mapper))
                    .build();
            ApiInterface = client.create(ApiInterface.class);

        }
        return ApiInterface;
    }


    public interface ApiInterface {

        @GET("/users/aerdy")
        Observable<retrofit2.Response<ResponseBody>> getResponsebody();

        @GET("/users/aerdy")
        Observable<ProfileGSON> getGSON();

        @GET("/users/aerdy")
        Observable<ProfileJackson> getJackSon();

    }


}

