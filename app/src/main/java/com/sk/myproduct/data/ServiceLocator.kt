package com.sk.myproduct.data

import android.content.Context
import com.sk.myproduct.data.source.DefaultTaskRepository
import com.sk.myproduct.data.source.remote.ApiCall

object ServiceLocator {

    private val apiCall = ApiCall()
    @Volatile
    var taskRepository: DefaultTaskRepository? = null

    fun provideTasksRepository(context: Context): DefaultTaskRepository {
        synchronized(this){
            return taskRepository
                ?: createTasksRepository(
                    context
                )
        }
    }

    private fun createTasksRepository(context: Context): DefaultTaskRepository {
        val newRepo = DefaultTaskRepository(apiCall)
        taskRepository = newRepo
        return newRepo
    }
}