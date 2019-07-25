package com.crupee.apitest.controller.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by nitesh on 3/20/15.
 */
public class WebUtil {

    public static String convertStreamToString(final InputStream is){

        BufferedReader br = null;
        StringBuilder sb = null;
        String line = null;

        try {
            br = new BufferedReader(new InputStreamReader(is, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }


        try {

            sb = new StringBuilder();
            while((line = br.readLine())!=null){
                sb.append(line+"\n");

            }

        } catch (IOException e) {

            e.printStackTrace();
            return "";

        }finally{

            try {

                is.close();

            } catch (Exception e2) {

                e2.printStackTrace();
            }


        }

        return sb.toString();

    }


    /**
     *  @return   string in format {param1=a&param2=b&param3=c"}
     * */
    public static String getParams(Map<String, String> params) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> e: params.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(e.getKey()).append('=').append(e.getValue());
        }
        return sb.toString();
    }
}
