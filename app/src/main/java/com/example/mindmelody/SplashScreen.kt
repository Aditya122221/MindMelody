package com.example.mindmelody

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashScreen : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                enableEdgeToEdge()
                setContentView(R.layout.activity_splash_screen)
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

                val logo = findViewById<ImageView>(R.id.splashLogo)
                val appName = findViewById<TextView>(R.id.appName)
                val tagline = findViewById<TextView>(R.id.tagline)

                // Load animations
                val logoAnim = AnimationUtils.loadAnimation(this, R.anim.fade_zoom_in)
                val textAnim = AnimationUtils.loadAnimation(this, R.anim.slide_up)

                // Apply animations
                logo.startAnimation(logoAnim)
                appName.startAnimation(textAnim)
                tagline.startAnimation(textAnim)

                val sharedPreferences = getSharedPreferences("MindMelodyPref", MODE_PRIVATE)
                val isTutorialShown = sharedPreferences.getBoolean("tutorialShown", false)
                val isLogin = sharedPreferences.getBoolean("isLogin", false)

//                Handler(Looper.getMainLooper()).postDelayed({
//                        startActivity(Intent(this, TutorialPage::class.java))
//                        finish()
//                }, 3000)

                Handler(Looper.getMainLooper()).postDelayed({
                        if(isTutorialShown && !isLogin) {
                                val intent = Intent(this, Login::class.java)
                                startActivity(intent)
                                finish()
                        } else if(isTutorialShown && isLogin) {
                                val intent = Intent(this, MainActivity::class.java)
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