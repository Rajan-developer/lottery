package com.crupee.lottery.setting;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.crupee.lottery.R;
import com.crupee.lottery.dashboard.activity.MainActivity;
import com.crupee.lottery.utility.PrefUtils;

import java.util.Locale;

public class SettingLanguageActivity extends AppCompatActivity {

    //views
    ImageView toolbarBack;
    LinearLayout english_language, nepali_language;
    CheckBox english_checkbox, nepali_checkbox;

    //variables
    public static String TAG = SettingLanguageActivity.class.getName();
    String selectedLanguage;
    ProgressDialog progressDialog;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (PrefUtils.isLanguageSelected(SettingLanguageActivity.this) == true) {
            String language = PrefUtils.returnlanguageSelected(SettingLanguageActivity.this);
            Locale locale = new Locale(language);
            Locale.setDefault(locale);

            Resources res = getResources();
            Configuration config = new Configuration(res.getConfiguration());
            config.locale = locale;
            config.setLocale(locale);
            config.setLayoutDirection(locale);
            res.updateConfiguration(config, res.getDisplayMetrics());
        }


        setContentView(R.layout.activity_settinglanguage);

        initView();

        /* clcik methods*/
        clickMethod();

    }


    private void initView() {

        toolbarBack = (ImageView) findViewById(R.id.toolbarBack);
        english_language = (LinearLayout) findViewById(R.id.english_language);
        nepali_language = (LinearLayout) findViewById(R.id.nepali_language);
        english_checkbox = (CheckBox) findViewById(R.id.english_checkbox);
        nepali_checkbox = (CheckBox) findViewById(R.id.nepali_checkbox);

        selectedLanguage = PrefUtils.returnlanguageSelected(SettingLanguageActivity.this);
        if (selectedLanguage.equalsIgnoreCase("en")) {
            english_checkbox.setChecked(true);
            PrefUtils.saveLanguage(SettingLanguageActivity.this, true, "en", "1");
        }
        if (selectedLanguage.equalsIgnoreCase("ne")) {
            nepali_checkbox.setChecked(true);
            PrefUtils.saveLanguage(SettingLanguageActivity.this, true, "ne", "2");
        }
    }

    private void clickMethod() {

        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingLanguageActivity.this, SettingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        english_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                english_checkbox.setChecked(true);
                nepali_checkbox.setChecked(false);
                PrefUtils.saveLanguage(SettingLanguageActivity.this, true, "en", "1");

                Intent intent = new Intent(SettingLanguageActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

        });

        nepali_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                english_checkbox.setChecked(false);
                nepali_checkbox.setChecked(true);
                PrefUtils.saveLanguage(SettingLanguageActivity.this, true, "ne", "2");

                Intent intent = new Intent(SettingLanguageActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
