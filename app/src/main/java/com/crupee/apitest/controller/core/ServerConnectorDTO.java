package com.crupee.apitest.controller.core;


import java.util.Map;

/**
 * Created by lokex on 12/23/14.
 */
public class ServerConnectorDTO {

    private String urlToConnect;



    private Map<String,String> dataListNameValuePair;

    private String dataJsonString;

    public String getUrlToConnect() {
        return urlToConnect;
    }

    public void setUrlToConnect(String urlToConnect) {
        this.urlToConnect = urlToConnect;
    }

    public Map<String,String> getDataListNameValuePair() {
        return dataListNameValuePair;
    }

    public void setDataListNameValuePair(Map<String,String> dataListNameValuePair) {
        this.dataListNameValuePair = dataListNameValuePair;
    }

    public String getDataJsonString() {
        return dataJsonString;
    }

    public void setDataJsonString(String dataJsonString) {
        this.dataJsonString = dataJsonString;
    }
}
