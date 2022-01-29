package com.sk.myproduct.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.sk.myproduct.data.ServiceLocator
import com.sk.myproduct.data.source.TaskRepository

class AppController : Application() {

    val apiRepository: TaskRepository
        get() = ServiceLocator.provideTasksRepository(this)

    override fun onCreate() {
        super.onCreate()
    }

    fun checkInternet(): Boolean {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting ?: false
    }
}