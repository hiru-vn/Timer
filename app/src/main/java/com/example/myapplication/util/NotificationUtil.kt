package com.example.myapplication.util

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.myapplication.AppConstance

class NotificationUtil{
    companion object{
        private const val CHANNEL_ID_TIMER = "menu_timer"
        private const val CHANNEL_NAME_TIMER = "timer_app_timer"
        private const val CHANNEL_ID = 0
        fun showTimerExpired(context: Context){
            val startIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            startIntent.action = AppConstance.ACTION_START
            val startPendingIntent = PendingIntent.getBroadcast(context,0,startIntent,PendingIntent.FLAG_UPDATE_CURRENT)
            val nBuider = getBasicNotification(context, CHANNEL_ID_TIMER, true)
        }

        private fun getBasicNotification(context: Context, channeL_ID_TIMER: String, b: Boolean): Any {

        }

    }
}