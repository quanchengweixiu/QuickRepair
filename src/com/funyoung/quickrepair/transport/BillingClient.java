package com.funyoung.quickrepair.transport;

import android.content.Context;

import com.funyoung.quickrepair.api.ApiException;
import com.funyoung.quickrepair.api.CommonUtils;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.IOException;

public final class BillingClient extends HttpRequestExecutor {
    public BillingClient(Context context, HttpClient httpClient){
        super(context, httpClient);
    }

    private static final String MODULE = "Bill";

    private final static class Method {
        private static final String CREATE_BILL = "createBill";
    }

    public  String createBill(long uid) throws IOException, ApiException {
        HttpRequestBase request = new HttpRequestBuilder(HttpRequestBuilder.POST,
                MODULE, Method.CREATE_BILL)
                .parameter(API_PARAM_USER_ID, String.valueOf(uid))
                .create();
        return doRequest(request);
    }

    public static boolean createBill(Context context, final long uid) {
        BillingClient ac = new BillingClient(context, SimpleHttpClient.get());
        Exception exception = null;
        try {
            String result = ac.createBill(uid);
            return CommonUtils.parseBooleanResult(result);
        } catch (ClientProtocolException e) {
            exception = e;
            e.printStackTrace();
        } catch (IOException e) {
            exception = e;
            e.printStackTrace();
        } catch (Exception e) {
            exception = e;
            e.printStackTrace();
        } finally {
            postCheckApiException(context, exception);
        }
        return false;
    }
}
