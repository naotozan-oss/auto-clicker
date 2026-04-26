package com.example.autoclicker

data class ClickTask(
    val id: String,
    val name: String,
    val actions: List<ClickAction>,
    val loopCount: Int = 0, // 0 means infinite
    val intervalMs: Long = 1000
)

data class ClickAction(
    val x: Float,
    val y: Float,
    val actionType: ActionType = ActionType.CLICK,
    val delayMs: Long = 0,
    val endX: Float? = null,
    val endY: Float? = null,
    val durationMs: Long = 100
)

enum class ActionType {
    CLICK,
    SWIPE,
    LONG_PRESS
}
