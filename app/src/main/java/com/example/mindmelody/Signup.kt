package com.example.mindmelody

import android.content.ContentValues
import android.content.Intent
import com.example.mindmelody.databinding.ActivitySignupBinding
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import kotlinx.coroutines.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.net.toUri
import androidx.transition.Visibility

class Signup : AppCompatActivity() {

        private lateinit var binding: ActivitySignupBinding
        private var otpCode = "2563"
        private var isSign = false

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                enableEdgeToEdge()

                binding = ActivitySignupBinding.inflate(layoutInflater)
                setContentView(binding.root)

                ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
                        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                        insets
                }

                setupClickListeners()
        }

        private fun setupClickListeners() {
                binding.btnSignUp.setOnClickListener {
                        if(isSign) {
                                handleSignUp()
                        } else {
                                handleOtp()
                        }
                }

                binding.tvLoginBtn.setOnClickListener {
                        navigateToLogin()
                }
        }

        private fun handleOtp() {
                val name = binding.etName.text.toString().trim()
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()

                binding.tilName.error = null
                binding.tilEmail.error = null
                binding.tilPassword.error = null

                if (!validateInputs(name,email, password)) return

                showLoading(true)

                CoroutineScope(Dispatchers.Main).launch {
                        try {
                                delay(2000)
                                showLoading(false)
                                binding.tilOTP.visibility = View.VISIBLE
                                binding.btnSignUp.text = "Verify OTP"
                                isSign = true
                        } catch (e: Exception) {
                                showLoading(false)
                                showToast("Sign up failed. Please try again.")
                        }
                }
        }

        private fun handleSignUp() {
                if(binding.etOTP.text.toString().trim().equals(otpCode)) {
                        val dbCon = DatabaseConnection(this)
                        val db = dbCon.writableDatabase
                        val userId = generateUserId()
                        val values = ContentValues().apply{
                                put("fullName", binding.etName.text.toString().trim())
                                put("email", binding.etEmail.text.toString().trim())
                                put("password", binding.etPassword.text.toString().trim())
                                put("created_at", System.currentTimeMillis().toString())
                        }

                        val result = db.insert("Users", null, values)
                        if (result != -1L) {
                                Toast.makeText(this, "User registered!", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, Login::class.java))
                                finish()
                        } else {
                                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                        }
                } else {
                        binding.tilOTP.error = "OTP is incorrect"
                }
        }

        private fun generateUserId() : Int {
                var userId = 0

                return userId
        }

        private fun validateInputs(name: String, email: String, password: String): Boolean {
                var isValid = true

                if(name.isEmpty()) {
                        binding.tilName.error = "Name is required"
                        isValid = false
                }

                if (email.isEmpty()) {
                        binding.tilEmail.error = "Email is required"
                        isValid = false
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        binding.tilEmail.error = "Please enter a valid email address"
                        isValid = false
                }

                if (password.isEmpty()) {
                        binding.tilPassword.error = "Password is required"
                        isValid = false
                } else if (password.length < 6) {
                        binding.tilPassword.error = "Password must be at least 6 characters"
                        isValid = false
                }

                return isValid
        }

        private fun showLoading(show: Boolean) {
                if (show) {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.btnSignUp.text = ""
                        binding.btnSignUp.isEnabled = false
                } else {
                        binding.progressBar.visibility = View.GONE
                        binding.btnSignUp.text = "Sign Up"
                        binding.btnSignUp.isEnabled = true
                }
        }

        private fun navigateToLogin() {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
        }

        private fun showToast(message: String) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
}
