package com.crupee.lottery.dashboard.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.crupee.lottery.R;
import com.crupee.lottery.dashboard.custom_navigation.MenuItem;
import com.crupee.lottery.dashboard.custom_navigation.SNavigationDrawer;
import com.crupee.lottery.dashboard.fragment.AboutusFragment;
import com.crupee.lottery.dashboard.fragment.BlogFragment;
import com.crupee.lottery.dashboard.fragment.ContactusFragment;
import com.crupee.lottery.dashboard.fragment.HomeFragment;
import com.crupee.lottery.utility.PrefUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //views
    SNavigationDrawer sNavigationDrawer;

    //variables
    int color1 = 0;
    Class fragmentClass;
    public static Fragment fragment;
    public static final int PERMISSIONS_MULTIPLE_REQUEST = 123;

    //instances


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*clear the previous lottery list from preference*/
        PrefUtils.clearLotteryList(MainActivity.this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestRuntimePermission();
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        sNavigationDrawer = findViewById(R.id.navigationDrawer);
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Home", R.drawable.news_bg));
        menuItems.add(new MenuItem("About Us", R.drawable.news_bg));
        menuItems.add(new MenuItem("Blog", R.drawable.news_bg));
        menuItems.add(new MenuItem("Contact Us", R.drawable.news_bg));
        sNavigationDrawer.setMenuItemList(menuItems);
        fragmentClass = HomeFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
        }


        sNavigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClicked(int position) {
                System.out.println("Position " + position);

                switch (position) {
                    case 0: {
                        color1 = R.color.red;
                        fragmentClass = HomeFragment.class;
                        break;
                    }
                    case 1: {
                        color1 = R.color.orange;
                        fragmentClass = AboutusFragment.class;
                        break;
                    }
                    case 2: {
                        color1 = R.color.green;
                        fragmentClass = BlogFragment.class;
                        break;
                    }
                    case 3: {
                        color1 = R.color.yellow;
                        fragmentClass = ContactusFragment.class;
                        break;
                    }

                }
                sNavigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {

                    @Override
                    public void onDrawerOpened() {

                    }

                    @Override
                    public void onDrawerOpening() {

                    }

                    @Override
                    public void onDrawerClosing() {
                        System.out.println("Drawer closed");

                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (fragment != null) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();

                        }
                    }

                    @Override
                    public void onDrawerClosed() {

                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        System.out.println("State " + newState);
                    }
                });
            }
        });


    }


    //For run time permission
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestRuntimePermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) +
                ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CAMERA) +
                ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (MainActivity.this, Manifest.permission.CAMERA) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                Snackbar.make(MainActivity.this.findViewById(android.R.id.content),
                        "Please Grant Permissions to capture image",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onClick(View v) {
                                requestPermissions(
                                        new String[]{Manifest.permission
                                                .READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        PERMISSIONS_MULTIPLE_REQUEST);
                            }
                        }).show();
            } else {
                requestPermissions(
                        new String[]{Manifest.permission
                                .READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSIONS_MULTIPLE_REQUEST);
            }
        } else {
            // write your logic code if permission already granted
        }

    }

    @Override
    public void onBackPressed() {

        PrefUtils.clearLotteryList(MainActivity.this);
        finish();
    }

}
