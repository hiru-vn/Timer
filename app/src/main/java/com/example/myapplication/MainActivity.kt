package com.example.myapplication

import android.app.PendingIntent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.health.TimerStat
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import com.example.myapplication.util.PrefUtil

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    enum class TimerState{
        Stop,Pause,Play
    }
    private lateinit var timer :CountDownTimer
    private var timerCountLength = 0L
    private var timerState = TimerState.Stop
    private var secondsRemanining = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setIcon(R.drawable.ic_timer)
        supportActionBar?.title = "Timer"

        fab_play.setOnClickListener { view ->
            startTimer()
            timerState = TimerState.Play
            updateButton()
        }
        fab_pause.setOnClickListener { view ->
            cancelTimer()
            timerState = TimerState.Play
            updateButton()
        }
        fab_stop.setOnClickListener { view ->
            cancelTimer()
            timerState = TimerState.Play
            onTimerFinished()
        }
    }

    private fun cancelTimer() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResume(){
        super.onResume()
        initTimer()

        //TODO: remove background timer
    }

    private fun initTimer() {
        timerState = PrefUtil.getTimerState(this)
        if (timerState == TimerState.Stop)
            setNewTimerLength()
        else
            setPreTimerLength()
        secondsRemanining = if (timerState == TimerState.Play || timerState == TimerState.Pause)
            PrefUtil.getSecondRemaining(this)
        else
            timerCountLength
        //TODO: change second remain to background timer stop

        if (timerState == TimerState.Play)
            startTimer()
        updateButton()
        updateCoundownUI()
    }
    private fun onTimerFinished(){
        timerState = TimerState.Stop

        setNewTimerLength()
        progress_countdown.progress = 0
        PrefUtil.setSecondRemaining(timerCountLength, this)
        secondsRemanining = timerCountLength

        updateButton()
        updateCoundownUI()
    }
    private fun startTimer(){
        timerState = TimerState.Play
        timer = object : CountDownTimer(secondsRemanining * 1000, 1000){
            override fun onFinish() = onTimerFinished()
            override fun onTick(milliFinsh: Long) {
                secondsRemanining = milliFinsh/1000
                updateCoundownUI()
            }
        }.start()
    }
    private fun setNewTimerLength(){
        val lengthInMinutes = PrefUtil.getTimerLength(this)
        timerCountLength = (lengthInMinutes * 60L)
        progress_countdown.max = timerCountLength.toInt()
    }
    private fun setPreTimerLength(){
        timerCountLength = PrefUtil.getPreviousTimerLengthSecond(this)
        progress_countdown.max = timerCountLength.toInt()
    }
    private fun updateCoundownUI(){
        val minsTillFinish = secondsRemanining/60
        val secondInMinTillFinish = secondsRemanining - minsTillFinish * 60
        val secondStr = secondInMinTillFinish.toString()
        if (secondStr.length == 2)
            textView.text = "$minsTillFinish:$secondStr"
        else
            textView.text = "$minsTillFinish:0$secondStr"
        progress_countdown.progress = (timerCountLength - secondsRemanining).toInt()
    }
    private fun updateButton(){
        when (timerState){
            TimerState.Play ->{
                fab_play.isEnabled = false
                fab_pause.isEnabled = true
                fab_stop.isEnabled = true
            }
            TimerState.Stop ->{
                fab_play.isEnabled = true
                fab_pause.isEnabled = false
                fab_stop.isEnabled = false
            }
            TimerState.Pause ->{
                fab_play.isEnabled = true
                fab_pause.isEnabled = false
                fab_stop.isEnabled = true
            }
        }
    }
    override fun onPause() {
        super.onPause()

        if (timerState == TimerState.Play){
            timer.cancel()
            //TODO: start background timer
        } else if(timerState == TimerState.Pause) {
            //TODO: show notification
        }
        PrefUtil.setPreviousTimerLengthSecond(timerCountLength, this)
        PrefUtil.setSecondRemaining(secondsRemanining,this)
        PrefUtil.setTimerState(timerState, this)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
