package com.example.autoclicker

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TaskRepository(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("autoclicker_tasks", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveTask(task: ClickTask) {
        val tasks = getAllTasks().toMutableList()
        val existingIndex = tasks.indexOfFirst { it.id == task.id }
        if (existingIndex != -1) {
            tasks[existingIndex] = task
        } else {
            tasks.add(task)
        }
        val json = gson.toJson(tasks)
        sharedPreferences.edit().putString("tasks_list", json).apply()
    }

    fun getAllTasks(): List<ClickTask> {
        val json = sharedPreferences.getString("tasks_list", null) ?: return emptyList()
        val type = object : TypeToken<List<ClickTask>>() {}.type
        return gson.fromJson(json, type)
    }

    fun deleteTask(taskId: String) {
        val tasks = getAllTasks().toMutableList()
        tasks.removeIf { it.id == taskId }
        val json = gson.toJson(tasks)
        sharedPreferences.edit().putString("tasks_list", json).apply()
    }

    fun getTaskById(taskId: String): ClickTask? {
        return getAllTasks().find { it.id == taskId }
    }
}
