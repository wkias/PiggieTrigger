package com.piggie.iemtrigger

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast

class FloatWindowManager(private val context: Context) {

    private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var floatView: View? = null

    fun showFloatingWindow() {
        if (floatView != null) {
            Toast.makeText(context, "悬浮窗已存在", Toast.LENGTH_SHORT).show()
            return
        }

        // 设置悬浮窗参数
        val layoutParams = WindowManager.LayoutParams().apply {
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                @Suppress("DEPRECATION")
                WindowManager.LayoutParams.TYPE_PHONE
            }

            // FLAG_NOT_FOCUSABLE 让悬浮窗不抢占键盘焦点
            flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN

            format = PixelFormat.TRANSLUCENT
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.CENTER // 1. 调整到屏幕中央，防止偏出屏幕外
        }

        // 创建明显的悬浮按钮
        floatView = Button(context).apply {
            text = "点击开启"
            setBackgroundColor(Color.RED) // 2. 设置显眼的红色背景
            setTextColor(Color.WHITE)
            setPadding(30, 20, 30, 20)

            setOnClickListener {
                KeyClickService.isEnabled = !KeyClickService.isEnabled
                if (KeyClickService.isEnabled) {
                    text = "ON"
                    setBackgroundColor(Color.GREEN) // 开启变绿
                } else {
                    text = "OFF"
                    setBackgroundColor(Color.RED)   // 关闭变红
                }
            }
        }

        try {
            windowManager.addView(floatView, layoutParams)
            Toast.makeText(context, "悬浮窗已创建", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "悬浮窗创建失败: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    fun removeFloatingWindow() {
        floatView?.let {
            windowManager.removeView(it)
            floatView = null
        }
    }
}