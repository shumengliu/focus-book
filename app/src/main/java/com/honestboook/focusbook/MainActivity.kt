package com.honestboook.focusbook

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController


class MainActivity : AppCompatActivity() {
    val TAG:String = "MainActivity"

    private lateinit var grantAccessibilityPermissionBtn: Button
    private lateinit var grantOverlayPermissionBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Main activity is starting")
        setContentView(R.layout.activity_main)

        setUpPermissionButtons()

        setupActionBarWithNavController(findNavController(R.id.fragment))
    }

    private fun setUpPermissionButtons() {
        grantAccessibilityPermissionBtn = findViewById(R.id.grant_accessibility_permission_btn)
        grantAccessibilityPermissionBtn.setOnClickListener {
            val intent: Intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
        }

        grantOverlayPermissionBtn = findViewById(R.id.grant_overlay_permission_btn)
        grantOverlayPermissionBtn.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
                startActivity(intent)
//                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    private fun isAccessibilitySettingOn(context: Context): Boolean{
        var accessibilityEnabled: Int = 0
//        val service:String = "$packageName/${BlockService.}"
        return true
    }
}