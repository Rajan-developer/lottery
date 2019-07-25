package com.crupee.apitest.controller;

import android.app.Activity;
import android.content.Context;
import android.text.LoginFilter;
import android.util.Log;
import android.widget.Toast;

import com.crupee.apitest.controller.core.HttpTaskListener;
import com.crupee.apitest.controller.core.ParserFamily;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rajan Shrestha on 8/21/2017.
 */

public class LoginHelper extends ParserFamily {

    private final String TAG = LoginHelper.class.getSimpleName();

    private HttpTaskListener mCallback;
    private int taskId = taskIdDefault;


    private Activity context;

    public LoginHelper(Activity context) {
        this.context = context;

    }

    @Override
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public int getTaskId() {
        return taskId;
    }

    @Override
    public void setParserCallBack(HttpTaskListener httpTaskListener) {
        this.mCallback = httpTaskListener;
    }

    @Override
    public HttpTaskListener getParserCallback() {
        return mCallback;
    }

    @Override
    public void handleServerResponse(String response) {

        Log.d(TAG, "Login Response::" + response);

        try {
            if (response.length() != 0) {
                mCallback.onAPiResponseObtained(taskId, response);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public Context getContext() {
        return null;
    }
}
