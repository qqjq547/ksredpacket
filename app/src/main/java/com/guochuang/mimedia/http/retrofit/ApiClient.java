package com.guochuang.mimedia.http.retrofit;

import android.support.v4.util.ArrayMap;

import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.JniUtil;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.tools.StringUtil;
import com.sz.gcyh.KSHongBao.BuildConfig;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
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
    public static String DEV_API_URL="http://dev_api.guochuangyuanhe.com/";
    public static String DEV_H5_URL="http://dev_h5.guochuangyuanhe.com/";
    public static String TEST_API_URL ="http://test_gateway.guochuangyuanhe.com/";
    public static String TEST_H5_URL ="http://120.77.110.100/";
    public static String RELEASE_API_URL ="https://api.guochuangyuanhe.com/";
    public static String RELEASE_H5_URL="https://www.guochuangyuanhe.com/";
    public static String HTML_URL= getDebuHtmlHost();

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
                String h_system_code = Constant.H_SYSTEM_CODE;
                String h_version = BuildConfig.VERSION_NAME;
                String h_sign ="";
                ArrayMap<String,String> map=new ArrayMap<>();
                if(request.body() instanceof FormBody){
                    FormBody oldFormBody = (FormBody) request.body();
                    for (int i=0;i<oldFormBody.size();i++){
                        map.put(oldFormBody.name(i),oldFormBody.value(i));
                    }
                }else {
                    HttpUrl httpUrl = request.url();
                    Set<String> names = httpUrl.queryParameterNames();
                    for (String key : names) {
                        map.put(key, httpUrl.queryParameter(key));
                    }
                }
                map.put(Constant.PARAMS_H_TENANT_CODE, h_tenant_code);
                map.put(Constant.PARAMS_H_TIME, h_time);
                map.put(Constant.PARAMS_H_NONCE, h_nonce);
                String secret=StringUtil.toSort(map)+"&secretValue=" ;
                if (ApiClient.getDebugHost().equals(ApiClient.RELEASE_API_URL)){
                    secret=secret+JniUtil.getSign();
                }else {
                    secret=secret+"*2f4961%8*5B588463bee04djDAed27";
                }
                h_sign =  StringUtil.md5(secret);
                request = request.newBuilder()
                        .addHeader(Constant.PARAMS_H_API_TOEKN, h_api_token)
                        .addHeader(Constant.PARAMS_H_TIME, h_time)
                        .addHeader(Constant.PARAMS_H_TENANT_CODE, h_tenant_code)
                        .addHeader(Constant.PARAMS_H_NONCE, h_nonce)
                        .addHeader(Constant.PARAMS_H_SYSTEM_CODE, h_system_code)
                        .addHeader(Constant.PARAMS_H_VERSION, h_version)
                        .addHeader(Constant.PARAMS_H_SIGN, h_sign)
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


    private ApiClient() {
        apiStores = Retrofit(getDebugHost()).create(ApiStore.class);
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
    public static String getDebugHost(){
        if (Constant.isDebug) {
            int debugHost = PrefUtil.getInstance().getInt(PrefUtil.DEBUGHOST, Constant.DEFAULT_HOST);
            switch (debugHost) {
                case 0://测试host
                    return TEST_API_URL;
                case 1://生产host
                    return RELEASE_API_URL;
                case 2://开发host
                    return DEV_API_URL;
            }
        }
        return RELEASE_API_URL;
    }
    public static String getDebuHtmlHost(){
        if (Constant.isDebug){
            int debugHost=PrefUtil.getInstance().getInt(PrefUtil.DEBUGHOST,Constant.DEFAULT_HOST);
            switch (debugHost){
                case 0://测试host
                    return TEST_H5_URL;
                case 1://生产host
                    return RELEASE_H5_URL;
                case 2://开发host
                    return DEV_H5_URL;
            }
        }
        return RELEASE_H5_URL;
    }
    public static void changeEnv(){
        instances = new ApiClient();
    }
}
