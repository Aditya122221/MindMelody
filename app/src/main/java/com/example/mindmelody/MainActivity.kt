package com.example.mindmelody

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
        private var selectedTab = 1
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

                val homeLayout = findViewById<LinearLayout>(R.id.llHome)
                val contactLayout = findViewById<LinearLayout>(R.id.llContact)
                val profileLayout = findViewById<LinearLayout>(R.id.llProfile)

                val homeImage = findViewById<ImageView>(R.id.imgHome)
                val contactImage = findViewById<ImageView>(R.id.ivContact)
                val profileImage = findViewById<ImageView>(R.id.ivProfile)

                val homeTxt = findViewById<TextView>(R.id.tvHome)
                val contactTxt = findViewById<TextView>(R.id.tvContact)
                val profileTxt = findViewById<TextView>(R.id.tvProfile)

                supportFragmentManager
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.cl, HomeFragment::class.java, null)
                        .commit()

                homeLayout.setOnClickListener {
                        if (selectedTab != 1) {
                                contactTxt.visibility = View.GONE
                                profileTxt.visibility = View.GONE

                                contactImage.setImageResource(R.drawable.contact_icon)
                                profileImage.setImageResource(R.drawable.profile_icon)

                                contactLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent))
                                profileLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent))

                                homeTxt.visibility = View.VISIBLE
                                homeImage.setImageResource(R.drawable.home_selected_icon)
                                homeLayout.setBackgroundResource(R.drawable.round_back_home_100)

                                val scaleAnimation = ScaleAnimation(
                                        0.8f, 1.0f, 1f, 1f,
                                        Animation.RELATIVE_TO_SELF, 0.0f,
                                        Animation.RELATIVE_TO_SELF, 0.0f
                                )
                                scaleAnimation.duration = 200
                                scaleAnimation.fillAfter = true
                                homeLayout.startAnimation(scaleAnimation)

                                supportFragmentManager
                                        .beginTransaction()
                                        .setReorderingAllowed(true)
                                        .replace(R.id.cl, HomeFragment::class.java, null)
                                        .commit()

                                selectedTab = 1
                        }
                }

                contactLayout.setOnClickListener {
                        if (selectedTab != 2) {
                                homeTxt.visibility = View.GONE
                                profileTxt.visibility = View.GONE

                                homeImage.setImageResource(R.drawable.home_icon)
                                profileImage.setImageResource(R.drawable.profile_icon)

                                homeLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent))
                                profileLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent))

                                contactTxt.visibility = View.VISIBLE
                                contactImage.setImageResource(R.drawable.contact_selected_icon)
                                contactLayout.setBackgroundResource(R.drawable.round_back_contact_100)

                                val scaleAnimation = ScaleAnimation(
                                        0.8f, 1.0f, 1f, 1f,
                                        Animation.RELATIVE_TO_SELF, 0.0f,
                                        Animation.RELATIVE_TO_SELF, 0.0f
                                )
                                scaleAnimation.duration = 200
                                scaleAnimation.fillAfter = true
                                contactLayout.startAnimation(scaleAnimation)

                                supportFragmentManager
                                        .beginTransaction()
                                        .setReorderingAllowed(true)
                                        .replace(R.id.cl, ContactFragment::class.java, null)
                                        .commit()

                                selectedTab = 2
                        }
                }

                profileLayout.setOnClickListener {
                        if (selectedTab != 3) {
                                contactTxt.visibility = View.GONE
                                homeTxt.visibility = View.GONE

                                contactImage.setImageResource(R.drawable.contact_icon)
                                homeImage.setImageResource(R.drawable.home_icon)

                                contactLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent))
                                homeLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent))

                                profileTxt.visibility = View.VISIBLE
                                profileImage.setImageResource(R.drawable.profile_selected_icon)
                                profileLayout.setBackgroundResource(R.drawable.round_back_profile_100)

                                val scaleAnimation = ScaleAnimation(
                                        0.8f, 1.0f, 1f, 1f,
                                        Animation.RELATIVE_TO_SELF, 0.0f,
                                        Animation.RELATIVE_TO_SELF, 0.0f
                                )
                                scaleAnimation.duration = 200
                                scaleAnimation.fillAfter = true
                                profileLayout.startAnimation(scaleAnimation)

                                supportFragmentManager
                                        .beginTransaction()
                                        .setReorderingAllowed(true)
                                        .replace(R.id.cl, ProfileFragment::class.java, null)
                                        .commit()


                                selectedTab = 3
                        }
                }
        }
}