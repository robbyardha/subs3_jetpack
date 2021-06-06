package com.ardhacodes.subs1_jetpack.utils
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource
object EspressoIdlingResource {
    private const val RESOURCE = "GLOBAL"
    val espressoTestIdlingResource = CountingIdlingResource(RESOURCE)

    fun CountIncrement(){
        espressoTestIdlingResource.increment()
    }

    fun CountDecrement(){
        espressoTestIdlingResource.decrement()
    }

    fun getEspressoIdlingResource(): IdlingResource {
        return espressoTestIdlingResource
    }
}