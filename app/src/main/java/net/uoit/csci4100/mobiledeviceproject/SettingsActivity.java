package net.uoit.csci4100.mobiledeviceproject;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;


public class SettingsActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;

    boolean location_service, notification_service, media_service;
    SharedPreferences preferences;
    SwitchCompat locationService, notificationService, mediaService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // changes preferences
        preferences = getSharedPreferences("PREFS", 0);
        location_service = preferences.getBoolean("locationService", false);
        notification_service = preferences.getBoolean("notificationService", false);
        media_service = preferences.getBoolean("mediaService", true);

        locationService = (SwitchCompat) findViewById(R.id.locationService);
        notificationService = (SwitchCompat) findViewById(R.id.notificationService);
        mediaService = (SwitchCompat) findViewById(R.id.mediaService);
//        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.userProfile_toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        locationService.setChecked(location_service);
        notificationService.setChecked(notification_service);
        mediaService.setChecked(media_service);

        // change preference for location service
        locationService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location_service = !location_service;
                locationService.setChecked(location_service);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("locationService", location_service);
                editor.apply();
            }
        });

        // change preference for notification service
        notificationService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification_service = !notification_service;
                notificationService.setChecked(notification_service);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("notificationService", notification_service);
                editor.apply();
            }
        });

        // change preference for media
        mediaService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                media_service = !media_service;
                mediaService.setChecked(media_service);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("mediaService", media_service);
                editor.apply();
            }
        });
    }
}