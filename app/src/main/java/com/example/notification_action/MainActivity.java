package com.example.notification_action;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.nio.channels.Channel;

import static com.example.notification_action.AppNotify.CHANNEL_1;
public class MainActivity extends AppCompatActivity {

    TextView textView, textView1;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        button = findViewById( R.id.button );
        textView = findViewById( R.id.title );
        textView1 = findViewById( R.id.message );
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = textView.getText().toString();
                String message = textView1.getText().toString();
                Intent activityIntent = new Intent( getApplicationContext(), MainActivity.class );
                PendingIntent contentIntent = PendingIntent.getActivity(
                        getApplicationContext(), 0, activityIntent, 0 );
                
                Intent broadcastIntent = new Intent( getApplicationContext(), NotificationReceiver.class );
                broadcastIntent.putExtra( "toast", message );
                PendingIntent actionIntent = PendingIntent.getBroadcast(
                        getApplicationContext(), 0,
                        broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT );
                
                Bitmap largeIcon = BitmapFactory.decodeResource(
                        getResources(), R.drawable.dfd );
                
                NotificationManagerCompat nmc = NotificationManagerCompat.from( getApplicationContext() );
                Notification notification = new NotificationCompat.Builder( getApplicationContext(), CHANNEL_1 )
                        .setSmallIcon( R.drawable.ic_android_black_24dp )
                        .setContentTitle( title )
                        .setContentText( message )
                        .setLargeIcon( largeIcon )
                        .setStyle( new NotificationCompat.BigTextStyle()
                                           .bigText( getString( R.string.about ) )
                                           .setBigContentTitle( "Big Content Title" )
                                           .setSummaryText( "Summary Text" ))
//                        .setStyle( new NotificationCompat.BigPictureStyle()
//                                           .bigPicture( largeIcon )
//                                           .bigLargeIcon( null )
//                        )
                        .setPriority( NotificationCompat.PRIORITY_HIGH )
                        .setCategory( NotificationCompat.CATEGORY_MESSAGE )
                        .setColor( Color.BLUE )
                        .setContentIntent( contentIntent )
                        .setAutoCancel( true )
                        .addAction( R.mipmap.ic_launcher, "Toast", actionIntent )
                        .build();
                nmc.notify( 1, notification );
            }
        } );
    }
}
