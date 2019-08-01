package com.crupee.lottery.utility.customUI;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.crupee.lottery.R;

public class CustomLoading extends Dialog {
    public CustomLoading(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_loading);
    }

}
