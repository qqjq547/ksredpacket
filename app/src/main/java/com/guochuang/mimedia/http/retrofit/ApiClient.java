package com.guochuang.mimedia.http.retrofit;

import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.PrefUtil;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    private static ApiClient instances;
    private ApiStore apiStores;
    public static OkHttpClient okHttpClient;
    public static String DEV_URL="http://dev.guochuangyuanhe.com:7005/";
    public static String TEST_URL="https://test_gateway.guochuangyuanhe.com/";
    public static String RELEASE_URL="https://api.guochuangyuanhe.com/";
    public static String HTML_URL=
            Constant.isDebug ?
            "http://120.77.110.100/":
            "https://www.guochuangyuanhe.com/";
    public static String API_SERVER_URL =
            Constant.isDebug ?
                    TEST_URL:
                    RELEASE_URL;

    public static Retrofit Retrofit(final String baseUrl) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                String h_api_token = PrefUtil.getInstance().getUserToken();
                String h_time = String.valueOf(System.currentTimeMillis());
                String h_tenant_code = Constant.TENANTCODE;
                String h_nonce = UUID.randomUUID().toString();
                request = request.newBuilder()
                        .addHeader(Constant.PARAMS_H_API_TOEKN, h_api_token)
                        .addHeader(Constant.PARAMS_H_TIME, h_time)
                        .addHeader(Constant.PARAMS_H_TENANT_CODE, h_tenant_code)
                        .addHeader(Constant.PARAMS_H_NONCE, h_nonce)
                        .build();
                return chain.proceed(request);
            }
        });
        if (Constant.isDebug) {
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logInterceptor);
        }

        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(15, TimeUnit.SECONDS);
        builder.writeTimeout(15, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        builder.sslSocketFactory(createSSLSocketFactory());
        builder.hostnameVerifier(new HostnameVerifier() {

            @Override
            public boolean verify(String hostname, SSLSession session) {
                //强行返回true 即验证成功
                return true;
            }
        });
        okHttpClient = builder.build();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return mRetrofit;
    }

    public static ApiClient getInstance() {
        if (instances == null) {
            instances = new ApiClient();
        }
        return instances;
    }

    public static ApiStore newInstance(String baseUrl) {
        return Retrofit(baseUrl).create(ApiStore.class);
    }

    private ApiClient() {
        apiStores = Retrofit(API_SERVER_URL).create(ApiStore.class);
    }

    public ApiStore getApiStores() {
        return apiStores;
    }
    public static class MyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {}

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {return new X509Certificate[0];}
    }
    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new MyTrustManager()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ssfFactory;
    }
}
