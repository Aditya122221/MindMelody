package com.example.mindmelody

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class TutorialPage : AppCompatActivity() {
        private lateinit var viewPager: ViewPager2
        private lateinit var dotsLayout: LinearLayout
        private lateinit var btnNext: Button
        private lateinit var btnSkip: TextView
        private lateinit var btnGetStarted: Button
        private lateinit var backgroundGradient: View

        private var currentPage = 0
        private val pageTransformer = CustomPageTransformer()

        private val onboardingItems = listOf(
                OnboardingItem(
                        "Voice Mood Detection",
                        "Tap the mic and say how you feel.",
                        R.drawable.ic_voice_mood,
                        R.color.voice_gradient_start,
                        R.color.voice_gradient_end
                ),
                OnboardingItem(
                        "Text Mood Input",
                        "Type your mood in one word or sentence.",
                        R.drawable.ic_text_mood,
                        R.color.text_gradient_start,
                        R.color.text_gradient_end
                ),
                OnboardingItem(
                        "Face Mood Detection",
                        "Let your face do the talking.",
                        R.drawable.ic_face_mood,
                        R.color.face_gradient_start,
                        R.color.face_gradient_end
                ),
                OnboardingItem(
                        "Spotify Integration",
                        "Stream curated playlists based on your mood.",
                        R.drawable.ic_spotify_mood,
                        R.color.spotify_gradient_start,
                        R.color.spotify_gradient_end
                )
        )
        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                enableEdgeToEdge()
                setContentView(R.layout.activity_tutorial_page)
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
                setupFullscreenMode()
                initViews()
                setupViewPager()
                setupDots()
                setupButtons()
                setupAnimations()
        }

        private fun initViews() {
                viewPager = findViewById(R.id.viewPager)
                dotsLayout = findViewById(R.id.dotsLayout)
                btnNext = findViewById(R.id.btnNext)
                btnSkip = findViewById(R.id.btnSkip)
                btnGetStarted = findViewById(R.id.btnGetStarted)
        }

        private fun setupViewPager() {
                val adapter = OnboardingAdapter(this)
                viewPager.adapter = adapter

                viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                        override fun onPageSelected(position: Int) {
                                updateDots(position)
                                updateButtons(position)
                                animateBackground(position)
                        }
                })
        }

        private fun setupDots() {
                for (i in onboardingItems.indices) {
                        val dot = View(this)
                        val params = LinearLayout.LayoutParams(24, 24)
                        params.setMargins(8, 0, 8, 0)
                        dot.layoutParams = params
                        dot.setBackgroundResource(R.drawable.dot_inactive)
                        dotsLayout.addView(dot)
                }
                updateDots(0)
        }

        private fun updateDots(position: Int) {
                for (i in 0 until dotsLayout.childCount) {
                        val dot = dotsLayout.getChildAt(i)
                        if (i == position) {
                                dot.setBackgroundResource(R.drawable.dot_active)
                                animateDot(dot, true)
                        } else {
                                dot.setBackgroundResource(R.drawable.dot_inactive)
                                animateDot(dot, false)
                        }
                }
        }

        private fun animateDot(dot: View, isActive: Boolean) {
                val animator = ValueAnimator.ofFloat(dot.scaleX, if (isActive) 1.2f else 1.0f)
                animator.duration = 200
                animator.interpolator = AccelerateDecelerateInterpolator()
                animator.addUpdateListener { animation ->
                        val scale = animation.animatedValue as Float
                        dot.scaleX = scale
                        dot.scaleY = scale
                }
                animator.start()
        }

        private fun updateButtons(position: Int) {
                if (position == onboardingItems.size - 1) {
                        btnNext.visibility = View.GONE
                        btnGetStarted.visibility = View.VISIBLE
                        btnSkip.visibility = View.GONE
                } else {
                        btnNext.visibility = View.VISIBLE
                        btnGetStarted.visibility = View.GONE
                        btnSkip.visibility = View.VISIBLE
                }
        }

        private fun animateBackground(position: Int) {
                val item = onboardingItems[position]
                val background = findViewById<View>(R.id.backgroundGradient)

                // Animate background color transition
                val colorFrom = ContextCompat.getColor(this,
                        if (position > 0) onboardingItems[position - 1].gradientStart else item.gradientStart)
                val colorTo = ContextCompat.getColor(this, item.gradientStart)

                val colorAnimation = ValueAnimator.ofArgb(colorFrom, colorTo)
                colorAnimation.duration = 300
                colorAnimation.addUpdateListener { animator ->
                        background.setBackgroundColor(animator.animatedValue as Int)
                }
                colorAnimation.start()
        }

        private fun setupFullscreenMode() {
                WindowCompat.setDecorFitsSystemWindows(window, false)
                val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
                windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
                windowInsetsController.systemBarsBehavior =
                        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        private fun setupButtons() {
                btnNext.setOnClickListener {
                        val current = viewPager.currentItem
                        if (current < onboardingItems.size - 1) {
                                viewPager.currentItem = current + 1
                        }
                }

                btnSkip.setOnClickListener {
                        finishOnboarding()
                }

                btnGetStarted.setOnClickListener {
                        finishOnboarding()
                }
        }

        private fun finishOnboarding() {
                // Navigate to main app
                val intent = Intent(this, Signup::class.java)
                startActivity(intent)
                finish()
                overridePendingTransition(R.anim.fade_in, R.anim.fade_in)
        }

        inner class OnboardingAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
                override fun getItemCount(): Int = onboardingItems.size

                override fun createFragment(position: Int): Fragment {
                        return OnboardingFragment.newInstance(onboardingItems[position])
                }
        }

        private fun setupAnimations() {
                // Add subtle floating animation to icon cards
                val floatingAnimator = ValueAnimator.ofFloat(0f, 10f, 0f)
                floatingAnimator.duration = 3000
                floatingAnimator.repeatCount = ValueAnimator.INFINITE
                floatingAnimator.interpolator = AccelerateDecelerateInterpolator()
                floatingAnimator.start()

                // Add particle effects for visual enhancement
                createParticleEffect()
        }

        private fun createParticleEffect() {
                // Create floating particles in background for enhanced visual appeal
//                val particleContainer = findViewById<ViewGroup>(R.id.particleContainer)
                // Implementation for floating particles would go here
        }

        inner class CustomPageTransformer : ViewPager2.PageTransformer {
                override fun transformPage(view: View, position: Float) {
                        val pageWidth = view.width
                        val pageHeight = view.height

                        when {
                                position < -1 -> view.alpha = 0f
                                position <= 1 -> {
                                        view.alpha = 1f
                                        view.translationX = pageWidth * -position
                                        view.scaleX = Math.max(0.85f, 1 - Math.abs(position) * 0.15f)
                                        view.scaleY = Math.max(0.85f, 1 - Math.abs(position) * 0.15f)
                                }
                                else -> view.alpha = 0f
                        }
                }
        }
}