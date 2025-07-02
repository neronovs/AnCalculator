package ru.narod.nod.ancalculator

import com.google.firebase.crashlytics.FirebaseCrashlytics

class App : android.app.Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase Crashlytics
        FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = true
    }
}