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
import com.google.firebase.auth.FirebaseAuth

class Signup : AppCompatActivity() {

        private lateinit var binding: ActivitySignupBinding
        private val dbConn = DatabaseConnection(this)

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
                        handleSignUp()
                }

                binding.tvLoginBtn.setOnClickListener {
                        navigateToLogin()
                }
        }

        private fun handleSignUp() {
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
                                if(dbConn.isUserEmailExists(email)) {
                                        showLoading(false)
                                        showToast("Email id already exists. Try logging.")
                                } else {
                                        FirebaseAuth.getInstance()
                                                .createUserWithEmailAndPassword(email, password)
                                                .addOnCompleteListener { task ->
                                                        if (task.isSuccessful) {
                                                                val user =
                                                                        FirebaseAuth.getInstance().currentUser
                                                                user?.sendEmailVerification()
                                                                        ?.addOnCompleteListener { verifyTask ->
                                                                                if (verifyTask.isSuccessful) {
                                                                                        val userId =
                                                                                                generateUserId()
                                                                                        val db =
                                                                                                dbConn.writableDatabase
                                                                                        val values =
                                                                                                ContentValues().apply {
                                                                                                        put(
                                                                                                                "userId",
                                                                                                                userId
                                                                                                        )
                                                                                                        put(
                                                                                                                "fullName",
                                                                                                                name
                                                                                                        )
                                                                                                        put(
                                                                                                                "email",
                                                                                                                email
                                                                                                        )
                                                                                                        put(
                                                                                                                "password",
                                                                                                                password
                                                                                                        )
                                                                                                        put(
                                                                                                                "created_at",
                                                                                                                System.currentTimeMillis()
                                                                                                                        .toString()
                                                                                                        )
                                                                                                }

                                                                                        val result =
                                                                                                db.insert(
                                                                                                        "Users",
                                                                                                        null,
                                                                                                        values
                                                                                                )
                                                                                        showToast("Verification email sent. Please check your email")
                                                                                        binding.etName.text!!.clear()
                                                                                        binding.etEmail.text!!.clear()
                                                                                        binding.etPassword.text!!.clear()
                                                                                        showLoading(false)
                                                                                } else {
                                                                                        showToast("Failed to send verification email: ${verifyTask.exception?.message}")
                                                                                }
                                                                        }
                                                        } else {
                                                                showToast("Signup failed: ${task.exception?.message}")
                                                        }
                                                }
                                }
                        } catch (e: Exception) {
                                showLoading(false)
                                showToast("Sign up failed. Please try again.")
                        }
                }
        }

        private fun generateUserId(): Int {
                var userId: Int
                do {
                        userId = (100000..999999).random()
                } while (dbConn.isUserIdExists(userId))
                return userId
        }

        private fun passwordReset(email: String) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                        Toast.makeText(this, "Check your email", Toast.LENGTH_SHORT).show()
                                } else {
                                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
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
