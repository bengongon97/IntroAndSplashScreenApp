package com.example.menes.introscreentrialapp;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    final String CHANNEL_ID = "1";

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Some channel name";
            String description = "Some channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button notifyButton = findViewById(R.id.notifyButton);


        notifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int notificationId = 1;

                createNotificationChannel();

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.muharrir.net/"));
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                final PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);


                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ovidos_logo)
                        .setContentTitle(getString(R.string.notificationTitle))
                        .setContentText(getString(R.string.clickToRedirect))
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setTicker("New Message Alert!") //Accessibility services
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setColor(ContextCompat.getColor(getBaseContext(), R.color.dot_light_screen1));



                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
                // notificationId is a unique int for each notification that you must define
                notificationManager.notify(notificationId, mBuilder.build());
            }
        });


        Button langButton = findViewById(R.id.langButton);




        langButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String languageToLoad = "";
                String country = "";
                String appCurrent = getResources().getConfiguration().locale.getLanguage();

                if (appCurrent.equals("tr")) {
                    languageToLoad = "en";
                    country = "US";
                }
                else if (appCurrent.equals("en")) {
                    languageToLoad = "tr";
                    country = "TR";
                }
 
                Locale locale = new Locale(languageToLoad, country);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                Context context = getBaseContext();
                context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());


                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        Button popUp =  findViewById(R.id.popUpbuton);

        popUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle(getString(R.string.titlePopup));
                builder.setMessage(getString(R.string.messagePopup));

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, getString(R.string.goodToast),Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, getString(R.string.reallyToast),Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }
}
