package com.example.myapplication.util

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.myapplication.AppConstance
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import java.text.SimpleDateFormat

class NotificationUtil{
    companion object{
        private const val CHANNEL_ID_TIMER = "menu_timer"
        private const val CHANNEL_NAME_TIMER = "timer app"
        private const val CHANNEL_ID = 0
        fun showTimerExpired(context: Context){
            val startIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            startIntent.action = AppConstance.ACTION_START
            val startPendingIntent = PendingIntent.getBroadcast(context,0,startIntent,PendingIntent.FLAG_UPDATE_CURRENT)
            val nBuilder = getBasicNotification(context, CHANNEL_ID_TIMER, true)
            nBuilder.setContentTitle("Time expired!")
                .setContentText("Start again?")
                .setContentIntent(getPendingIntentWithStack(context, MainActivity::class.java))
                .addAction(R.drawable.ic_play_arrow,"Start",startPendingIntent)
            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEL_NAME_TIMER,true)
            nManager.notify(CHANNEL_ID, nBuilder.build())
        }
        fun showTimerRunning(context: Context, wakeUpTime:Long){
            val stopIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            stopIntent.action = AppConstance.ACTION_STOP
            val stopPendingIntent = PendingIntent.getBroadcast(context,0,stopIntent,PendingIntent.FLAG_UPDATE_CURRENT)

            val pauseIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            pauseIntent.action = AppConstance.ACTION_PAUSE
            val pausePendingIntent = PendingIntent.getBroadcast(context,0,pauseIntent,PendingIntent.FLAG_UPDATE_CURRENT)

            val dateFormat = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT)
            val nBuilder = getBasicNotification(context, CHANNEL_ID_TIMER, true)
            nBuilder.setContentTitle("Time is running")
                .setContentText("End: ${dateFormat.format(wakeUpTime)}")
                .setContentIntent(getPendingIntentWithStack(context, MainActivity::class.java))
                .addAction(R.drawable.ic_stop,"Stop",stopPendingIntent)
                .addAction(R.drawable.ic_pause,"Pause",pausePendingIntent)
                .setOngoing(true)
            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEL_NAME_TIMER,true)
            nManager.notify(CHANNEL_ID, nBuilder.build())
        }
        fun showTimerPause(context: Context){
            val resumeIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            resumeIntent.action = AppConstance.ACTION_RESUME
            val resumePendingIntent = PendingIntent.getBroadcast(context,0,resumeIntent,PendingIntent.FLAG_UPDATE_CURRENT)
            val nBuilder = getBasicNotification(context, CHANNEL_ID_TIMER, true)
            nBuilder.setContentTitle("Time pause!")
                .setContentText("Resume?")
                .setContentIntent(getPendingIntentWithStack(context, MainActivity::class.java))
                .addAction(R.drawable.ic_play_arrow,"Resume",resumePendingIntent)
            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEL_NAME_TIMER,true)
            nManager.notify(CHANNEL_ID, nBuilder.build())
        }
        private fun getBasicNotification(context: Context, channel_ID_TIMER: String, playSound: Boolean): NotificationCompat.Builder {
            val notificationSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(context,channel_ID_TIMER)
                .setSmallIcon(R.drawable.ic_timer)
                .setDefaults(0)
                .setAutoCancel(true)
            if (playSound){
                notificationBuilder.setSound(notificationSound)
            }
            return notificationBuilder
        }
        private fun <T> getPendingIntentWithStack(context: Context, javaClass: Class<T>):PendingIntent{
            val resultIntent = Intent(context,javaClass)
            resultIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or  Intent.FLAG_ACTIVITY_SINGLE_TOP
            val stackBuilder = TaskStackBuilder.create(context)
            stackBuilder.addParentStack(javaClass)
            stackBuilder.addNextIntent(resultIntent)

            return stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT)
        }
        fun hideTimerNotification(context: Context){
            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.cancel(CHANNEL_ID)
        }
        @TargetApi(26)
        private fun NotificationManager.createNotificationChannel(channelID: String, channelName: String, playSound: Boolean){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val channelImportance = if(playSound) NotificationManager.IMPORTANCE_DEFAULT
                                        else NotificationManager.IMPORTANCE_LOW
                val nChannel = NotificationChannel(channelID, channelName,channelImportance)
                nChannel.enableLights(true)
                nChannel.lightColor = Color.BLUE
                this.createNotificationChannel(nChannel)
            }
        }
    }
}