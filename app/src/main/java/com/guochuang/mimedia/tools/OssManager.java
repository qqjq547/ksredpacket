package com.guochuang.mimedia.tools;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCustomSignerCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.http.response.HttpResponse;
import com.guochuang.mimedia.http.retrofit.ApiClient;

import java.io.File;
import java.io.IOException;

import static cn.jiguang.share.android.api.AbsPlatform.getApplicationContext;

public class OssManager {
    public interface OnResultListener{
        void onStart();
        void onProgress(int progress);
        void onSuccess(String url);
        void onFail(String code,String errmsg);
    }
    OSSCustomSignerCredentialProvider credentialProvider;
    String endPoint="http://oss-cn-shenzhen.aliyuncs.com";
    String bucketName="sz-gcyh";
    OSS oss;

    public OssManager() {
        credentialProvider= new OSSCustomSignerCredentialProvider() {
            @Override
            public String signContent(String content) {
                retrofit2.Call<HttpResponse<String>> call=ApiClient.getInstance().getApiStores().getOssToken(content);
                try {
                    retrofit2.Response<HttpResponse<String>> respone=call.execute();
                    String result=respone.body().getResponse();
                    LogUtil.d("result="+result);
                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        oss = new OSSClient(getApplicationContext(), endPoint, credentialProvider, conf);
    }

    public void uploadVideo(String uploadFilePath, final OnResultListener onResultListener){
        String objectKey=PrefUtil.getInstance().getString(PrefUtil.VIDEO_FOLDER,"video")+File.separator+App.getInstance().getUserInfo().getId()+"_"+System.currentTimeMillis()+".mp4";
        PutObjectRequest put = new PutObjectRequest(bucketName, objectKey, uploadFilePath);
         final String url=endPoint.replaceFirst("//","//"+bucketName+".")+File.separator+objectKey;
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {

                @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                onResultListener.onProgress((int)(currentSize*100/totalSize));
            }
        });

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                onResultListener.onSuccess(url);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                if (clientExcepion != null) {
                    clientExcepion.printStackTrace();
                    onResultListener.onFail("ClientException",clientExcepion.getMessage());
                }else if(serviceException != null){
                    onResultListener.onFail(serviceException.getErrorCode(),serviceException.getRawMessage());
                }
            }
        });
        onResultListener.onStart();

    }
}
