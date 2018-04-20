package com.android.f45tv.f45techdashboard.Client;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class RetrofitClient {

    public static String BASE_URL;
    public static Retrofit retrofit;
    public static TLSSocketFactory socket;
    public static TrustManager[] trustManagers;
    public static X509TrustManager trustManager;
    private static TrustManagerFactory trustManagerFactory;




    public void setBaseUrl(String url){
        BASE_URL = url;
    }

    public static String getBaseUrl(){
        return BASE_URL;
    }


    public static Retrofit getClient(){


        /*   https://stackoverflow.com/questions/31002159/now-that-sslsocketfactory-is-deprecated-on-android-what-would-be-the-best-way-t/31003269#31003269
        *
        *   SSL SOCKET FACTORY DEPRECATED FIX
        *   BUG FOR ANDROID 4.4 BELOW
        *   NEED TO ENABLE TLS SOCKET
        *   SSL SOCKET FACTORY DEPRECATED SO YOU NEED TO HAVE 2 PARAMETERS
        *   SOCKET AND TRUST MANAGER SPECIFICALLY X509TrustManager
        *
        * */
        {
            try {
                trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init((KeyStore) null);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }
        }
        trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
        }
        trustManagers = trustManagerFactory.getTrustManagers();
        trustManager = (X509TrustManager) trustManagers[0];


        try {
            socket = new TLSSocketFactory(trustManager);
        } catch (KeyManagementException e) {
            e.printStackTrace();
            Log.e(TAG, "getClient: keymanagementexception" );
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.e(TAG, "getClient: nosuchalgo" );
        }

        /*
        * ADDING REQUEST TIMEOUT FOR FETCHING
        *
        * */
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .sslSocketFactory(socket,trustManager)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }

}
