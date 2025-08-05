package com.example.mindmelody

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mindmelody.databinding.ActivityProfilePageBinding

class ProfilePage : AppCompatActivity() {
        private lateinit var binding : ActivityProfilePageBinding
        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                enableEdgeToEdge()
                setContentView(R.layout.activity_profile_page)
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

                binding = ActivityProfilePageBinding.inflate(layoutInflater)
                setContentView(binding.root)

                setupClickListeners()
                loadUserData()
        }

        private fun setupClickListeners() {
                binding.btnLogout.setOnClickListener {
                        val sharedPref = getSharedPreferences("MindMelodyPref", MODE_PRIVATE)
                        val editor = sharedPref.edit()
                        editor.putBoolean("isLogin", false)
                        editor.putString("email", null)
                        editor.apply()
                        val intent = Intent(this, Login::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                }
        }

        private fun loadUserData() {
                val dbConn = DatabaseConnection(this)
                val sharedPref = getSharedPreferences("MindMelodyPref", MODE_PRIVATE)
                val email = sharedPref.getString("email", "example@gmail.com")
                email?.let {
                        val u = dbConn.getUserByEmail(it)
                        binding.etEmail.setText(u?.email)
                        binding.etFullName.setText(u?.name)
                }
        }
}