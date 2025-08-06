package com.example.mindmelody

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mindmelody.databinding.FragmentProfileBinding
import androidx.core.content.edit

class ProfileFragment : Fragment() {
        private lateinit var binding: FragmentProfileBinding
        private lateinit var sharedPref : SharedPreferences

        override fun onCreateView(
                inflater: LayoutInflater, container: ViewGroup?,
                savedInstanceState: Bundle?
        ): View? {
                binding = FragmentProfileBinding.inflate(inflater, container, false)
                sharedPref =requireContext().getSharedPreferences("MindMelodyPref", Context.MODE_PRIVATE)
                return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                super.onViewCreated(view, savedInstanceState)

                val dbConn = DatabaseConnection(requireContext())
                val email = sharedPref.getString("email", "example@gmail.com")
                email?.let {
                        val u = dbConn.getUserByEmail(it)
                        binding.etEmail.setText(u?.email)
                        binding.etFullName.setText(u?.name)
                }

                binding.btnLogout.setOnClickListener {
                        sharedPref.edit {
                                putBoolean("isLogin", false)
                                putString("email", null)
                        }
                        val intent = Intent(activity, Login::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                }
        }
}