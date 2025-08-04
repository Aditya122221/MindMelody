package com.example.mindmelody

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.mindmelody.databinding.ActivityLoginBinding
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Login : AppCompatActivity() {
        private lateinit var binding: ActivityLoginBinding
        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                enableEdgeToEdge()
                setContentView(R.layout.activity_login)
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
                binding = ActivityLoginBinding.inflate(layoutInflater)
                setContentView(binding.root)
                setupClickListeners()
        }

        private fun setupClickListeners() {
                // Login Button Click
                binding.btnLogin.setOnClickListener {
                        handleLogin()
                }

                // Forgot Password Click
                binding.tvForgotPassword.setOnClickListener {
                        handleForgotPassword()
                }

                // Register Text Click
                binding.tvRegister.setOnClickListener {
                        handleRegister()
                }
        }

        private fun handleLogin() {
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()

                // Validate inputs
                if (!validateInputs(email, password)) {
                        return
                }

                // Show loading state
                showLoading(true)

                // Simulate network call with 2-second delay
                Handler(Looper.getMainLooper()).postDelayed({
                        showLoading(false)

                        // Simulate successful login
                        Toast.makeText(this, "Login successful! Welcome back.", Toast.LENGTH_SHORT).show()

                        // Here you would typically navigate to the main activity
                        startActivity(Intent(this, HomePage::class.java))
                        finish()

                }, 2000)
        }

        private fun validateInputs(email: String, password: String): Boolean {
                var isValid = true

                // Clear previous errors
                binding.tilEmail.error = null
                binding.tilPassword.error = null

                // Validate email
                if (email.isEmpty()) {
                        binding.tilEmail.error = "Email is required"
                        isValid = false
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        binding.tilEmail.error = "Please enter a valid email address"
                        isValid = false
                }

                // Validate password
                if (password.isEmpty()) {
                        binding.tilPassword.error = "Password is required"
                        isValid = false
                }

                return isValid
        }

        private fun showLoading(isLoading: Boolean) {
                if (isLoading) {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.btnLogin.text = ""
                        binding.btnLogin.isEnabled = false
                } else {
                        binding.progressBar.visibility = View.GONE
                        binding.btnLogin.text = "Login"
                        binding.btnLogin.isEnabled = true
                }
        }

        private fun handleForgotPassword() {
                // Option 1: Show toast
                Toast.makeText(this, "Forgot password functionality coming soon!", Toast.LENGTH_SHORT).show()

                // Option 2: Open dummy URL (uncomment if needed)
                /*
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://yourapp.com/forgot-password"))
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(this, "Unable to open forgot password page", Toast.LENGTH_SHORT).show()
                }
                */
        }

        private fun handleRegister() {
                startActivity(Intent(this, Signup::class.java))
        }

        private fun showSocialLoginToast(provider: String) {
                Toast.makeText(this, "Login with $provider - coming soon!", Toast.LENGTH_SHORT).show()
        }

        override fun onDestroy() {
                super.onDestroy()
        }
}