package com.example.serviceexample

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class SoundPlayerService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private val NOTIFICATION_ID = 101

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.sample2)
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "ACTION_PLAY" -> {
                mediaPlayer?.start()
                Log.d("SoundPlayerService", "Playing sound")
            }
            "ACTION_PAUSE" -> {
                mediaPlayer?.pause()
                Log.d("SoundPlayerService", "Pausing sound")
            }
            "ACTION_STOP" -> {
                Log.d("SoundPlayerService", "Stopping service")
                stopForeground(true)
                stopSelf()
                return START_NOT_STICKY
            }
        }

        // Create and display the notification
        val notification = createNotificationWithControls()

        // Start as foreground with the proper type for Android 10+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(NOTIFICATION_ID, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK)
        } else {
            startForeground(NOTIFICATION_ID, notification)
        }

        return START_STICKY
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "sound_channel",
                "Sound Player",
                NotificationManager.IMPORTANCE_HIGH  // Changed to HIGH importance
            )
            channel.description = "Used for sound player controls"
            channel.setShowBadge(true)
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)

            // Verify channel was created
            val createdChannel = manager.getNotificationChannel("sound_channel")
            Log.d("SoundPlayerService", "Channel importance: ${createdChannel.importance}")
        }
    }

    private fun createNotificationWithControls(): Notification {
        // Intent to open MainActivity when notification is tapped
        val contentIntent = Intent(this, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(
            this, 0, contentIntent, PendingIntent.FLAG_IMMUTABLE
        )

        // Create action buttons intents
        val playIntent = Intent(this, SoundPlayerService::class.java).apply {
            action = "ACTION_PLAY"
        }
        val pauseIntent = Intent(this, SoundPlayerService::class.java).apply {
            action = "ACTION_PAUSE"
        }
        val stopIntent = Intent(this, SoundPlayerService::class.java).apply {
            action = "ACTION_STOP"
        }

        val playPendingIntent = PendingIntent.getService(
            this, 1, playIntent, PendingIntent.FLAG_IMMUTABLE
        )
        val pausePendingIntent = PendingIntent.getService(
            this, 2, pauseIntent, PendingIntent.FLAG_IMMUTABLE
        )
        val stopPendingIntent = PendingIntent.getService(
            this, 3, stopIntent, PendingIntent.FLAG_IMMUTABLE
        )

        // Build enhanced notification
        return NotificationCompat.Builder(this, "sound_channel")
            .setContentTitle("Sound Player")
            .setContentText("Sound is currently playing")  // Add this
            .setSmallIcon(android.R.drawable.ic_media_play)  // Using system icon
            .setPriority(NotificationCompat.PRIORITY_HIGH)  // Higher priority
            .setContentIntent(contentPendingIntent)         // Tap action
            .setOngoing(true)                              // Persistent
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)  // Show on lock screen
            .addAction(android.R.drawable.ic_media_play, "Play", playPendingIntent)
            .addAction(android.R.drawable.ic_media_pause, "Pause", pausePendingIntent)
            .addAction(android.R.drawable.ic_media_ff, "Stop", stopPendingIntent)
            .build()
    }

    override fun onBind(intent: Intent): IBinder? = null
}