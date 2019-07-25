package com.crupee.apitest.controller.core;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by lokex on 12/23/14.
 */
public class HttpPostServerConnector {

    private final String TAG = HttpPostServerConnector.class.getSimpleName();

    private ServerConnectorDTO serverConnectorDto;

    public HttpPostServerConnector(ServerConnectorDTO connector) {

        this.serverConnectorDto = connector;

    }

    public String connectServerWithHttpPostRequest() throws Exception {


       /* InputStream certificateInputStream = null;
        if (MyApplication.PRODUCTION) {
            certificateInputStream = MyApplication.context
                    .getResources().openRawResource(R.raw.production_cert);
            AppLog.showLog(TAG,"using production SSL certificate");
        } else {
            certificateInputStream = MyApplication.context
                    .getResources().openRawResource(R.raw.staging_cert);
            AppLog.showLog(TAG,"using staging SSL certificate");
        }

        KeyStore trustStore = KeyStore.getInstance("BKS");
        try{
            trustStore.load(certificateInputStream,
                    "re3d6Exe5HBsdskad8efj8CxZwv".toCharArray());
        } finally {
            certificateInputStream.close();
        }


        TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
        tmf.init(trustStore);
        AppLog.showLog(TAG,"SSL: did init TrustManagerFactory with trust keyStore");
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, tmf.getTrustManagers(), null);
        AppLog.showLog(TAG,"SSL: did init context with trust keyStore");*/



        /*SSLContext sslcontext = SSLContext.getInstance("TLSv1");
        sslcontext.init(null, null, null);
        SSLSocketFactory NoSSLv3Factory = new NoSSLv3SocketFactory(sslcontext.getSocketFactory());

        HttpsURLConnection.setDefaultSSLSocketFactory(NoSSLv3Factory);

        URL url  = new URL(serverConnectorDto.getUrlToConnect());
        HttpsURLConnection conn= (HttpsURLConnection) url.openConnection();
        conn.connect();
        *//*l_connection = (HttpsURLConnection) l_url.openConnection();
        l_connection.connect();*//*

         */
        Log.d(TAG, "*************CONNECTING SERVER*******");
        Log.d(TAG, "url to connect:: " + serverConnectorDto.getUrlToConnect());


        URL url = new URL(serverConnectorDto.getUrlToConnect());

        String postData = "";

        if (serverConnectorDto.getDataJsonString() != null) {

            postData = serverConnectorDto.getDataJsonString();
        } else {
            postData = WebUtil.getParams(serverConnectorDto.getDataListNameValuePair());
        }

        Log.d(TAG, "post data :: " + postData);

        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        //HttpsURLConnection conn= (HttpsURLConnection) url.openConnection();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod("POST");
        // conn.setRequestProperty( "Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setUseCaches(false);

        try {
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(postDataBytes);
            wr.flush();
            wr.close();

            int responseCode = conn.getResponseCode();
            Log.d(TAG, "response code::" + responseCode);

            if (responseCode == 201) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                Log.d(TAG, "response from server::" + response);

                return response.toString();

            }

            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                Log.d(TAG, "response from server::" + response);

                return response.toString();

            }

            if (responseCode == 404) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                Log.d(TAG, "server response:: " + response.toString());

                return response.toString();
            } else {
                Log.d(TAG, "Ignoring the connection since response code is not 200");
                return "";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }


    }

}
