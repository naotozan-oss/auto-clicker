package com.example.autoclicker

import android.os.Handler
import android.os.Looper

class AutoClickEngine(private val service: AutoClickService) {

    private val handler = Handler(Looper.getMainLooper())
    private var isRunning = false
    private var currentTask: ClickTask? = null
    private var actionIndex = 0
    private var currentLoop = 0

    fun startTask(task: ClickTask) {
        if (isRunning) return
        isRunning = true
        currentTask = task
        actionIndex = 0
        currentLoop = 0
        executeNext()
    }

    fun stopTask() {
        isRunning = false
        handler.removeCallbacksAndMessages(null)
    }

    private fun executeNext() {
        if (!isRunning) return
        val task = currentTask ?: return
        
        if (actionIndex >= task.actions.size) {
            actionIndex = 0
            currentLoop++
            if (task.loopCount > 0 && currentLoop >= task.loopCount) {
                stopTask()
                return
            }
        }

        val action = task.actions[actionIndex]
        
        when (action.actionType) {
            ActionType.CLICK -> service.click(action.x, action.y)
            ActionType.SWIPE -> {
                if (action.endX != null && action.endY != null) {
                    service.swipe(action.x, action.y, action.endX, action.endY, action.durationMs)
                }
            }
            ActionType.LONG_PRESS -> {
                // ロングプレスの実装（必要に応じて）
            }
        }

        actionIndex++
        
        val nextDelay = if (actionIndex < task.actions.size) {
            task.actions[actionIndex].delayMs
        } else {
            task.intervalMs
        }

        handler.postDelayed({
            executeNext()
        }, nextDelay)
    }
}
