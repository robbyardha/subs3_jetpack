package com.ardhacodes.subs1_jetpack

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

object LiveDataTest {
    fun <T> getValue(liveData: LiveData<T>): T {
        val dataArr = arrayOfNulls<Any>(1)
        val latchCount = CountDownLatch(1)

        val observer = object : Observer<T> {
            override fun onChanged(o: T) {
                dataArr[0] = o
                latchCount.countDown()
                liveData.removeObserver(this)
            }
        }

        liveData.observeForever(observer)

        try {
            latchCount.await(2, TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return dataArr[0] as T

    }
}