package com.example.mindmelody

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                enableEdgeToEdge()
                setContentView(R.layout.activity_main)
                ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                        v.setPadding(
                                systemBars.left,
                                systemBars.top,
                                systemBars.right,
                                systemBars.bottom
                        )
                        insets
                }

                val sharedPreferences = getSharedPreferences("MindMelodyPref", MODE_PRIVATE)
                val isTutorialShown = sharedPreferences.getBoolean("tutorialShown", false)
                val isLogin = sharedPreferences.getBoolean("isLogin", false)

                Handler(Looper.getMainLooper()).postDelayed({
                        if(isTutorialShown && !isLogin) {
                                val intent = Intent(this, Login::class.java)
                                startActivity(intent)
                                finish()
                        } else if(isTutorialShown && isLogin) {
                                val intent = Intent(this, HomePage::class.java)
                                startActivity(intent)
                                finish()
                        } else {
                                val intent = Intent(this, TutorialPage::class.java)
                                startActivity(intent)
                                finish()
                        }
                }, 3000)
        }
}