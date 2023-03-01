package com.pagination

import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
    }
}