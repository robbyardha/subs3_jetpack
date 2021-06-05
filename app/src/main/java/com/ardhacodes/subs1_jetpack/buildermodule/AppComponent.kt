package com.ardhacodes.subs1_jetpack.buildermodule

import android.app.Application
import com.ardhacodes.subs1_jetpack.RootApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuildersModule::class,
        AppModule::class,
        NetworkModule::class]
)
interface AppComponent : AndroidInjector<RootApp> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}