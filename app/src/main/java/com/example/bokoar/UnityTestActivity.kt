package com.example.bokoar

import android.os.Bundle
import android.util.Log
import com.unity3d.player.UnityPlayerGameActivity

class UnityTestActivity : UnityPlayerGameActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("UnityBridge", "Unity AR Activity started")
    }
}
