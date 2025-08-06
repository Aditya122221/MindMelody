package com.example.mindmelody

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.mindmelody.databinding.ActivityLoginBinding
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
        private lateinit var binding: ActivityLoginBinding
        private val dbConn = DatabaseConnection(this)
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

                val user = FirebaseAuth.getInstance().currentUser
                val userFromDB = dbConn.getUserByEmail(email)

                if (userFromDB != null) {
                        if (user != null) {
                                user.reload().addOnCompleteListener { reloadTask ->
                                        if (reloadTask.isSuccessful) {
                                                if (user.isEmailVerified) {
                                                        if (password == userFromDB.password) {
                                                                val sharedPref = getSharedPreferences("MindMelodyPref", MODE_PRIVATE)
                                                                val editor = sharedPref.edit()
                                                                editor.putBoolean("isLogin", true)
                                                                editor.putString("email", email)
                                                                editor.apply()
                                                                showLoading(false)
                                                                startActivity(Intent(this, MainActivity::class.java))
                                                                finish()
                                                        } else {
                                                                showToast("Password is incorrect")
                                                                showLoading(false)
                                                        }
                                                } else if (System.currentTimeMillis() - userFromDB.created_at.toLong() >= 60 * 60 * 1000) {
                                                        user.delete().addOnCompleteListener { deleteTask ->
                                                                if(deleteTask.isSuccessful) showToast("Email verification failed. Please sign up again")
                                                                else showToast("Email delete error")
                                                        }
                                                        dbConn.deleteUserByEmail(email)
                                                        showLoading(false)
                                                } else {
                                                        showToast("Email is not verified. Please verify it")
                                                        showLoading(false)
                                                }
                                        } else {
                                                showToast("Failed to refresh user")
                                                showLoading(false)
                                        }
                                }
                        } else {
                                showToast("User not logged in")
                                showLoading(false)
                        }
                } else {
                        showToast("User does not exist")
                        showLoading(false)
                }
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

        private fun showToast(msg: String) {
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        }

        override fun onDestroy() {
                super.onDestroy()
        }
}