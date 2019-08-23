package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.myapplication.util.PrefUtil

class TimerExpiredReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        //TODO : show notification
        PrefUtil.setTimerState(MainActivity.TimerState.Stop, context)
        PrefUtil.setAlarmSetTime(0, context)
    }
}
