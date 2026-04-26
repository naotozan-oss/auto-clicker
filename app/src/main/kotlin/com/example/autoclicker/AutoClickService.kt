package com.example.autoclicker

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.view.accessibility.AccessibilityEvent

class AutoClickService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // 必要に応じてイベントを処理
    }

    override fun onInterrupt() {
        // 割り込み時の処理
    }

    fun click(x: Float, y: Float) {
        val path = Path()
        path.moveTo(x, y)
        val builder = GestureDescription.Builder()
        builder.addStroke(GestureDescription.StrokeDescription(path, 0, 100))
        dispatchGesture(builder.build(), null, null)
    }

    fun swipe(startX: Float, startY: Float, endX: Float, endY: Float, duration: Long) {
        val path = Path()
        path.moveTo(startX, startY)
        path.lineTo(endX, endY)
        val builder = GestureDescription.Builder()
        builder.addStroke(GestureDescription.StrokeDescription(path, 0, duration))
        dispatchGesture(builder.build(), null, null)
    }

    companion object {
        var instance: AutoClickService? = null
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }
}
