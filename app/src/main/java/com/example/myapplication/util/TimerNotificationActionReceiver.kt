package com.example.myapplication.util

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.myapplication.AppConstance
import com.example.myapplication.MainActivity

class TimerNotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action){
            AppConstance.ACTION_STOP -> {
                MainActivity.removeAlarm(context)
                PrefUtil.setTimerState(MainActivity.TimerState.Stop, context)
                NotificationUtil.hideTimerNotification(context)
            }
            AppConstance.ACTION_PAUSE -> {
                var secondRemaining = PrefUtil.getSecondRemaining(context)
                val alarmSetTime = PrefUtil.getAlarmSetTime(context)
                val nowSeconds = MainActivity.nowSeconds

                secondRemaining -= nowSeconds - alarmSetTime
                PrefUtil.setTimerState(MainActivity.TimerState.Pause, context)
                NotificationUtil.showTimerPause(context)
            }
            AppConstance.ACTION_RESUME -> {
                val secondRemaining = PrefUtil.getSecondRemaining(context)
                val wakeUpTime = MainActivity.setAlarm(context, MainActivity.nowSeconds, secondRemaining)
                PrefUtil.setTimerState(MainActivity.TimerState.Play,context)
                NotificationUtil.showTimerRunning(context, wakeUpTime)
            }
            AppConstance.ACTION_START -> {
                val minutesRemaining = PrefUtil.getTimerLength(context)
                val secondsRemaining = minutesRemaining * 60L
                val wakeUpTime = MainActivity.setAlarm(context, MainActivity.nowSeconds, secondsRemaining)
                PrefUtil.setTimerState(MainActivity.TimerState.Play,context)
                PrefUtil.setSecondRemaining(secondsRemaining,context)
                NotificationUtil.showTimerRunning(context, wakeUpTime)
            }
        }
    }
}
