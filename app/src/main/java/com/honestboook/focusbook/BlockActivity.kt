package com.honestboook.focusbook

import android.content.Context
import android.graphics.PixelFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Button
import com.honestboook.focusbook.databinding.ActivityBlockBinding
import com.honestboook.focusbook.databinding.FragmentListBinding

class BlockActivity : AppCompatActivity() {
    private val TAG:String = "BlockActivity"
    private lateinit var hideBtn: Button

    private lateinit var binding: ActivityBlockBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Block Activity is starting")
        binding = ActivityBlockBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val mParams: WindowManager.LayoutParams? = WindowManager.LayoutParams( 600,
//            600,
//            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
//            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
//            PixelFormat.TRANSLUCENT
//        )
//
//        val popUpView = LayoutInflater.from(this).inflate(R.layout.activity_block, null)
//        val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        wm.addView(popUpView, mParams)
//
//        binding.hideBtn.setOnClickListener{
//            wm.removeView(popUpView)
//        }
    }
}