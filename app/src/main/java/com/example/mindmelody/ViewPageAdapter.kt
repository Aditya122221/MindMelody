package com.example.mindmelody

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        private val fragments = listOf(TextFragment(), SpeechFragment(), ImageFragment())

        override fun getItemCount(): Int {
                return fragments.count()
        }

        override fun createFragment(position: Int): Fragment = fragments[position]
}