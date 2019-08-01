package com.crupee.lottery.setting;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crupee.lottery.R;
import com.crupee.lottery.dashboard.activity.MainActivity;
import com.crupee.lottery.dashboard.adapter.LotteryDialogRecyclerViewAdapter;
import com.crupee.lottery.login.LoginActivity;
import com.crupee.lottery.utility.PrefUtils;

import org.w3c.dom.Text;

import java.util.Locale;

public class SettingActivity extends AppCompatActivity {

    //views
    ImageView toolbarBack;
    TextView settingtitleTV, btn_logout_profile, setting_language_tv;
    LinearLayout setting_select_language, setting_profile;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PrefUtils.isLanguageSelected(SettingActivity.this) == true) {
            String language = PrefUtils.returnlanguageSelected(SettingActivity.this);
            Locale locale = new Locale(language);
            Locale.setDefault(locale);

            Resources res = getResources();
            Configuration config = new Configuration(res.getConfiguration());
            config.locale = locale;
            config.setLocale(locale);
            config.setLayoutDirection(locale);
            res.updateConfiguration(config, res.getDisplayMetrics());
        }


        setContentView(R.layout.activity_setting);

        initView();

        clickMethod();
    }


    private void initView() {

        toolbarBack = (ImageView) findViewById(R.id.toolbarBack);
        settingtitleTV = (TextView) findViewById(R.id.settingTitleTV);
        btn_logout_profile = (TextView) findViewById(R.id.btn_logout_profile);
        setting_language_tv = (TextView) findViewById(R.id.setting_language_tv);
        setting_profile = (LinearLayout) findViewById(R.id.setting_profile);
        setting_select_language = (LinearLayout) findViewById(R.id.setting_select_language);

        if (PrefUtils.returnlanguageSelected(SettingActivity.this).equalsIgnoreCase("en")) {
            setting_language_tv.setText(R.string.english_language);
        } else if (PrefUtils.returnlanguageSelected(SettingActivity.this).equalsIgnoreCase("ne")) {
            setting_language_tv.setText(R.string.nepali_language);
        }


    }

    private void clickMethod() {

        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        setting_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showUserDialog();

            }
        });

        setting_select_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, SettingLanguageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_logout_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setMessage(getResources().getString(R.string.logout_dialog_message)).setTitle(getResources().getString(R.string.logout))
                        .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Choose the images from Gallery
                                processLogout();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.setCancelable(true);
                alert.show();
                alert.getButton(Dialog.BUTTON_NEGATIVE).
                        setTextColor(getResources().getColor(R.color.error));
                alert.getButton(Dialog.BUTTON_POSITIVE).
                        setTextColor(getResources().getColor(R.color.success));


            }
        });

    }

    private void showUserDialog() {

        final Dialog d = new Dialog(SettingActivity.this);
        d.setContentView(R.layout.dialog_profile);
        d.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final TextView name = (TextView) d.findViewById(R.id.user_name);
        final TextView email = (TextView) d.findViewById(R.id.user_email);

        name.setText(PrefUtils.returnUserName(SettingActivity.this));
        email.setText(PrefUtils.returnUserEmail(SettingActivity.this));

        Window dialogWindow = d.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);

        lp.y = 300; // The new position of the Y coordinates

        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // Width
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // Height

        // The system will call this function when the Window Attributes when the change, can be called directly by application of above the window parameters change, also can use setAttributes
        d.onWindowAttributesChanged(lp);
        dialogWindow.setAttributes(lp);

        d.show();
    }

    private void processLogout() {
        PrefUtils.Logout(SettingActivity.this);
        Intent splashIntent = new Intent(SettingActivity.this, LoginActivity.class);
        splashIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(splashIntent);
        finish();

    }


}
