package com.example.mindmelody

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.mindmelody.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
        private lateinit var binding : FragmentHomeBinding

        override fun onCreateView(
                inflater: LayoutInflater, container: ViewGroup?,
                savedInstanceState: Bundle?
        ): View? {
                binding = FragmentHomeBinding.inflate(inflater, container, false)
                return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                super.onViewCreated(view, savedInstanceState)

                val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
                val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)

                val adapter = ViewPageAdapter(this)
                viewPager.adapter = adapter

                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                        tab.text = when (position) {
                                0 -> "Text"
                                1 -> "Speech"
                                2 -> "Image"
                                else -> ""
                        }
                }.attach()
        }
}