package com.crupee.apitest.controller.core;

import android.content.Context;

/**
 * Created by lokex on 5/8/15.
 */
public abstract class ParserFamily {

    public int taskIdDefault = -1;

    /**set the task id for the current server communication operation*/
    public abstract void setTaskId(int taskId);

    /*returns the taskId for current server operation*/
    public abstract int getTaskId();

    /**set the listener to be invoked when the server communication task is completed.
     *Different methods are invoked on the basis of whether the task was successful or there were some errors.
     * @param httpTaskListener listener to be invoked when the server communication task is completed. Can be null.
     * */
    public abstract void setParserCallBack(HttpTaskListener httpTaskListener);

    /**returns the listener that will be invoked when the task is completed. Can return null*/
    public abstract HttpTaskListener getParserCallback();

    /**method that will be invoked when the server returns us the response.
     *The task of parsing the JSON response from server should be done here.*/
    public abstract void handleServerResponse(String response);

    /**returns the context used by the parser. Can be null*/
    public abstract Context getContext();

}
