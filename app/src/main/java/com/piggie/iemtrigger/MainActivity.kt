package com.piggie.iemtrigger

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var floatWindowManager: FloatWindowManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        floatWindowManager = FloatWindowManager(this)

        // 1. 创建按钮 1
        val btnAccessibility = Button(this).apply {
            text = "开启无障碍服务"
            setOnClickListener {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
        }

        // 2. 创建按钮 2
        val btnOverlay = Button(this).apply {
            text = "开启悬浮窗权限并显示"
            setOnClickListener {
                if (!Settings.canDrawOverlays(this@MainActivity)) {
                    val intent = Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:$packageName")
                    )
                    startActivity(intent)
                } else {
                    floatWindowManager.showFloatingWindow()
                }
            }
        }

        // 3. 按钮：关闭悬浮窗
        val btnCloseOverlay = Button(this).apply {
            text = "关闭悬浮窗"
            setOnClickListener {
                floatWindowManager.removeFloatingWindow()
            }
        }

        // 4. 按钮：退出应用
        val btnExitApp = Button(this).apply {
            text = "退出应用"
            // 设置明显的红色字体提示（可选）
            setTextColor(0xFFFF3333.toInt())
            setOnClickListener {
                // 先移除悬浮窗并关闭按键拦截
                floatWindowManager.removeFloatingWindow()
                // 关闭 Activity 并从最近任务清理
                finishAffinity()
                // 强制关闭进程
                kotlin.system.exitProcess(0)
            }
        }

        // 5. 构建垂直布局并添加边距
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 250, 50, 50)

            // 按顺序加入四个按钮
            addView(btnAccessibility)
            addView(btnOverlay)
            addView(btnCloseOverlay)
            addView(btnExitApp)
        }

        setContentView(layout)
    }
}