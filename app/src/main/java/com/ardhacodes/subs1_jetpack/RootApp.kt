package com.ardhacodes.subs1_jetpack

import com.ardhacodes.subs1_jetpack.buildermodule.AppComponent
import com.ardhacodes.subs1_jetpack.buildermodule.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class RootApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}