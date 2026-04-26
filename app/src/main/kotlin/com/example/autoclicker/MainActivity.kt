package com.example.autoclicker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnEnableAccessibility = findViewById<Button>(R.id.btn_enable_accessibility)
        btnEnableAccessibility.setOnClickListener {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
        }

        val btnCreateTask = findViewById<Button>(R.id.btn_create_task)
        btnCreateTask.setOnClickListener {
            // オーバーレイパーミッションの確認
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName"))
                startActivity(intent)
            } else {
                // サービスの開始
                startService(Intent(this, FloatingViewService::class.java))
            }
        }
    }
}
