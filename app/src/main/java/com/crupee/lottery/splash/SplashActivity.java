package com.crupee.lottery.splash;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crupee.lottery.R;
import com.crupee.lottery.dashboard.activity.MainActivity;
import com.crupee.lottery.login.LoginActivity;
import com.crupee.lottery.setting.SettingLanguageActivity;
import com.crupee.lottery.utility.PrefUtils;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {


    //variable
    private static String TAG = SplashActivity.class.getSimpleName();
    private static int SPLASH_TIME = 2000;

    //Views
    TextView english_tv, nepali_tv;
    LinearLayout bottom_sheet_splash;

    BottomSheetBehavior sheetBehavior;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PrefUtils.isLanguageSelected(SplashActivity.this)) {

            String language = PrefUtils.returnlanguageSelected(SplashActivity.this);
            Locale locale = new Locale(language);
            Locale.setDefault(locale);

            Resources res = getResources();
            Configuration config = new Configuration(res.getConfiguration());
            config.locale = locale;
            config.setLocale(locale);
            config.setLayoutDirection(locale);
            res.updateConfiguration(config, res.getDisplayMetrics());
        } else {

            String language = PrefUtils.returnlanguageSelected(SplashActivity.this);
            Locale locale = new Locale(language);
            Locale.setDefault(locale);

            Resources res = getResources();
            Configuration config = new Configuration(res.getConfiguration());
            config.locale = locale;
            config.setLocale(locale);
            config.setLayoutDirection(locale);
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        setContentView(R.layout.activity_splash);

        initView();

        clickMethod();


        /**
         * bottom sheet state change listener
         * we are changing button text when sheet changed state
         * */
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms

                if (PrefUtils.isLanguageSelected(SplashActivity.this)) {
                    goToLogin();
                } else {
                    toggleBottomSheet();
                }

            }
        }, SPLASH_TIME);


    }


    private void initView() {
        english_tv = (TextView) findViewById(R.id.english_tv);
        nepali_tv = (TextView) findViewById(R.id.nepali_tv);
        bottom_sheet_splash = (LinearLayout) findViewById(R.id.bottom_sheet_splash);

        sheetBehavior = BottomSheetBehavior.from(bottom_sheet_splash);
    }

    private void clickMethod() {
        english_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleBottomSheet();
                PrefUtils.saveLanguage(SplashActivity.this, true, "en", "1");
                goToLogin();
            }
        });

        nepali_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleBottomSheet();
                PrefUtils.saveLanguage(SplashActivity.this, true, "ne", "2");
                goToLogin();
            }
        });
    }

    public void toggleBottomSheet() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    private void goToLogin() {
        if (PrefUtils.returnLoggedIn(SplashActivity.this)) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }


}
