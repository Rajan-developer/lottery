package com.crupee.apitest.controller.core;

import android.os.AsyncTask;

/**
 * Created by binod on 12/23/14.
 */
public class ServerTask extends AsyncTask<Void,Void,String> {

    public enum RequestMethod{
        GET,
        POST
    }

    private final String TAG = ServerTask.class.getSimpleName();

    private ServerConnectorDTO mServerConnectorDTO;
    private RequestMethod method;
    private ParserFamily parser;

    /**
     * @param serverConnectorDTO instance of {@link ServerConnectorDTO}. Cannot be null
     *
     * */

    public ServerTask(ParserFamily parser, ServerConnectorDTO serverConnectorDTO){

        if(serverConnectorDTO!=null){
            mServerConnectorDTO = serverConnectorDTO;
        }else {
            throw new NullPointerException(serverConnectorDTO.getClass().toString()+" cannot be null");
        }

        this.parser = parser;

    }


    public void execute(RequestMethod method){
        if(method!=null){
           this.method = method;

            this.execute();
        }else {
            throw new NullPointerException("Request cannot be null");
        }

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(this.parser!=null && this.parser.getParserCallback()!=null){
            this.parser.getParserCallback().onTaskStarted(this.parser.getTaskId());
        }

    }

    @Override
    protected String doInBackground(Void... params) {

        String serverResponse = null;

        switch (method){
            case GET:
                try {
                    serverResponse = new HttpGetServerConnector(mServerConnectorDTO).connectWithGetRequest();
                } catch (Exception e) {
                    e.printStackTrace();
                    serverResponse = "";
                }
                break;

            case POST:
                try {
                    serverResponse = new HttpPostServerConnector(mServerConnectorDTO).connectServerWithHttpPostRequest();
                } catch (Exception e) {
                    e.printStackTrace();
                    serverResponse =  "";
                }
                break;

            default:
                throw new IllegalArgumentException("Request method not defined properly");
        }


        return serverResponse;
    }


    @Override
    protected void onCancelled() {
        super.onCancelled();

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

       if(this.parser!=null){
           this.parser.handleServerResponse(result);
       }

    }



}
