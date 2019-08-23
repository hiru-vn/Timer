package com.example.myapplication.util

import android.content.Context
import androidx.preference.PreferenceManager
import com.example.myapplication.MainActivity

class PrefUtil {
    companion object{
        fun getTimerLength(context: Context) : Int{
            return 1
        }
        private const val PRE_TIMER_LENGTH_SECONDS_ID = "com.example.myapplication.util.per_timer_length_seconds_id"

        fun getPreviousTimerLengthSecond(context: Context): Long{
            val preference = PreferenceManager.getDefaultSharedPreferences(context)
            return preference.getLong(PRE_TIMER_LENGTH_SECONDS_ID, 0)
        }
        fun setPreviousTimerLengthSecond(seconds: Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(PRE_TIMER_LENGTH_SECONDS_ID, seconds)
            editor.apply()

        }
        private const val TIMER_STATE_ID = "com.example.myapplication.util.timer_state_id"

        fun getTimerState(context: Context): MainActivity.TimerState{
            val preference = PreferenceManager.getDefaultSharedPreferences(context)
            val ordinal = preference.getInt(TIMER_STATE_ID, 0)
            return MainActivity.TimerState.values()[ordinal]
        }
        fun setTimerState(state: MainActivity.TimerState, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            val ordinal = state.ordinal
            editor.putInt(TIMER_STATE_ID, ordinal)
            editor.apply()
        }
        private const val SECOND_REMAINING = "com.example.myapplication.util.second_remaining"

        fun getSecondRemaining(context: Context): Long{
            val preference = PreferenceManager.getDefaultSharedPreferences(context)
            return preference.getLong(SECOND_REMAINING, 0)
        }
        fun setSecondRemaining(seconds: Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(SECOND_REMAINING, seconds)
            editor.apply()

        }
    }
}