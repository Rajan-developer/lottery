package com.crupee.apitest.controller.core;

import android.app.Activity;
import android.content.Context;

import com.crupee.apitest.controller.LoginHelper;


/**
 * Created by lokex on 5/8/15.
 */
public class ParserFactory {

          public enum ParserType{

              LOGIN_RESPONSE,


           }



     /**factory method that creates a parser that will handle the server response of different tasks\
      * @param context instance of current {@link Context}. Can be null
      * @param parserType the type of parser that determines instant creation.
      *
      *  @return an instance of {@link ParserFamily} that will handle the server responses
      * */
    public static ParserFamily create(Context context, ParserType parserType){

        if(parserType==null)  return null;

        switch (parserType){

            case LOGIN_RESPONSE:
                return new LoginHelper((Activity) context);



            default:
                return null;
        }


    }


}
