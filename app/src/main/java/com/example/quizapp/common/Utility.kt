package com.example.quizapp.common

import java.util.concurrent.TimeUnit

object Utility {
    fun getTimeFormat(millis: Long): CharSequence? {
        return String.format("%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(millis) -
                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), // The change is in this line
            TimeUnit.MILLISECONDS.toSeconds(millis) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }
}