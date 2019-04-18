package ru.wheelman.pryaniky_test

import android.app.Application
import android.util.Log
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.wheelman.pryaniky_test.di.networkModule

inline fun <reified T : Any> T.logd(obj: Any?) = Log.d(this::class.simpleName, obj.toString())

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    networkModule
                )
            )
        }
    }
}