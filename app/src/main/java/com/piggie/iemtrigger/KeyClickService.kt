package com.piggie.iemtrigger

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent

class KeyClickService : AccessibilityService() {

    companion object {
        var instance: KeyClickService? = null
        var targetX: Float = 721f
        var targetY: Float = 2958f
        var isEnabled: Boolean = false
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}

    override fun onInterrupt() {}

    override fun onKeyEvent(event: KeyEvent): Boolean {
        // 未开启功能时，不拦截按键，交给系统正常调节音量
        if (!isEnabled) {
            return super.onKeyEvent(event)
        }

        // 只在按键按下 (ACTION_DOWN) 时响应，避免按键抬起 (ACTION_UP) 时二次触发
        if (event.action == KeyEvent.ACTION_DOWN) {
            when (event.keyCode) {
                // 1. 音量上键：模拟屏幕点击
                KeyEvent.KEYCODE_VOLUME_UP -> {
                    performClick(targetX, targetY)
                    return true // 返回 true 拦截音量键，不弹出音量条
                }

                // 2. 音量下键：模拟系统返回键
                KeyEvent.KEYCODE_VOLUME_DOWN -> {
                    performGlobalAction(GLOBAL_ACTION_BACK)
                    return true // 返回 true 拦截音量键
                }
            }
        }

        return super.onKeyEvent(event)
    }

    /**
     * 执行指定坐标的屏幕手势点击
     */
    private fun performClick(x: Float, y: Float) {
        val path = Path().apply { moveTo(x, y) }
        val gesture = GestureDescription.Builder()
            .addStroke(GestureDescription.StrokeDescription(path, 0, 50))
            .build()

        dispatchGesture(gesture, object : GestureResultCallback() {
            override fun onCompleted(gestureDescription: GestureDescription?) {
            }

            override fun onCancelled(gestureDescription: GestureDescription?) {
            }
        }, null)
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }
}