package com.crupee.apitest.controller.core;

/**
 * Created by lokex on 12/23/14.
 */
public interface HttpTaskListener {

    /**method is invoked when result from rest-api is successfully obtained
     *
     * @param taskId the id for the task that was started with. The value will be -1 if no any value was used to distinguish the task
     * @param result the server result obtained with communication with rest-api. It will be one of DTOs used
     * */
    public void onAPiResponseObtained(int taskId, String result);


    /**method is invoked when no response could be obtained from rest-api
     * @param taskId the id for the task that was started with. The value will be -1 if no any value was used to distinguish the task
     *
     *@param reason the reason explaining the cause for api communication failure
     * */
    public void onApiResponseFailed(int taskId, FailureReason reason, String errorMessage, String errorCode);

    /**method is invoked when the server communication task is initiated.
     * @param taskId the id for the task that was started with. The value will be -1 if no any value was used to distinguish the task
     * */
    public void onTaskStarted(int taskId);

    /**method is invoked when the async task for server communication is cancelled.
     * @param taskId the id for the task that was started with. The value will be -1 if no any value was used to distinguish the task
     *
     * */
    public void onTaskCancelled(int taskId);


}
