package com.example.mindmelody

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mindmelody.databinding.FragmentContactBinding
import com.example.mindmelody.databinding.FragmentHomeBinding

class ContactFragment : Fragment() {
        private lateinit var binding : FragmentContactBinding

        override fun onCreateView(
                inflater: LayoutInflater, container: ViewGroup?,
                savedInstanceState: Bundle?
        ): View? {
                binding = FragmentContactBinding.inflate(inflater, container, false)
                return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                super.onViewCreated(view, savedInstanceState)

                binding.linkedinLogo.setOnClickListener {
                        val linkedInUrl = "https://www.linkedin.com/in/aditya-kumar-482429346/"

                        val linkedInIntent = Intent(Intent.ACTION_VIEW)
                        linkedInIntent.data =
                                Uri.parse("linkedin://in/aditya-kumar-482429346")

                        val packageManager: PackageManager? = context?.packageManager
                        if (linkedInIntent.resolveActivity(packageManager!!) != null) {
                                startActivity(linkedInIntent)
                        } else {
                                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedInUrl))
                                startActivity(webIntent)
                        }
                }

                binding.githubLogo.setOnClickListener {
                        val githubLink = "https://github.com/Aditya122221?tab=repositories"
                        val githubIntent = Intent(Intent.ACTION_VIEW, Uri.parse(githubLink))
                        startActivity(githubIntent)
                }
        }
}