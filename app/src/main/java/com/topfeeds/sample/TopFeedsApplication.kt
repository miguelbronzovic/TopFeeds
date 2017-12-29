/*
 * Created by Miguel Bronzovic.
 *
 * Top Feeds Application class
 */
package com.topfeeds.sample

import android.app.Application
import timber.log.Timber

class TopFeedsApplication : Application() {

    lateinit var appInstance: TopFeedsApplication

    override fun onCreate() {
        super.onCreate()

        appInstance = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}