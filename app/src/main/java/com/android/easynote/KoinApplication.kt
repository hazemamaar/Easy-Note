package com.android.easynote

import android.app.Application
import com.android.easynote.core.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class KoinApplication :Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@KoinApplication)
            modules(notesModule,viewModelModule,adaptersModule,useCaseModule)
        }

    }
}