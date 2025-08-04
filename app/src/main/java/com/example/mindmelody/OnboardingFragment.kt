package com.example.mindmelody

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView

class OnboardingFragment : Fragment() {

        companion object {
                private const val ARG_ITEM = "onboarding_item"

                fun newInstance(item: OnboardingItem): OnboardingFragment {
                        val fragment = OnboardingFragment()
                        val args = Bundle()
                        args.putParcelable(ARG_ITEM, item)
                        fragment.arguments = args
                        return fragment
                }
        }
        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
        }

        override fun onCreateView(
                inflater: LayoutInflater, container: ViewGroup?,
                savedInstanceState: Bundle?
        ): View? {
                // Inflate the layout for this fragment
                return inflater.inflate(R.layout.fragment_onboarding, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                super.onViewCreated(view, savedInstanceState)

                val item = arguments?.getParcelable<OnboardingItem>(ARG_ITEM) ?: return

                val iconCard = view.findViewById<CardView>(R.id.iconCard)
                val icon = view.findViewById<ImageView>(R.id.icon)
                val title = view.findViewById<TextView>(R.id.title)
                val description = view.findViewById<TextView>(R.id.description)

                icon.setImageResource(item.iconRes)
                title.text = item.title
                description.text = item.description

                // Animate views on creation
                animateViews(iconCard, title, description)
        }

        private fun animateViews(vararg views: View) {
                views.forEachIndexed { index, view ->
                        view.alpha = 0f
                        view.translationY = 100f

                        view.animate()
                                .alpha(1f)
                                .translationY(0f)
                                .setStartDelay((index * 100).toLong())
                                .setDuration(600)
                                .setInterpolator(AccelerateDecelerateInterpolator())
                                .start()
                }
        }
}