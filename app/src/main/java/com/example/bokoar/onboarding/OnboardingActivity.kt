package com.example.bokoar.onboarding
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.bokoar.databinding.ActivityOnboardingBinding
import com.example.bokoar.R
import com.example.bokoar.HomeActivity

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var pages: List<OnboardingPage>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Hide action bar
        supportActionBar?.hide()

        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pages = listOf(
            OnboardingPage(
                title = "Step Into Ratu Boko",
                description = "Explore history brought to life through augmented reality",
                iconRes = R.drawable.onboarding1
            ),
            OnboardingPage(
                title = "Explore at Your Own Pace",
                description = "Use the map to get a clear overview of Ratu Boko and find important spots around you. Take your time discovering each area as you move through the site.",
                iconRes = R.drawable.onboarding2
            ),

            OnboardingPage(
                title = "Hear the Stories of the Past",
                description = "Dive into the history and meaning behind every place you visit",
                iconRes = R.drawable.onboarding3
            )
        )

        val adapter = OnboardingAdapter(pages)
        binding.viewPager.adapter = adapter
        // connects dots indicator to the ViewPager
        binding.dotsIndicator.attachTo(binding.viewPager)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.btnNext.text =
                    if (position == pages.lastIndex) "Start Exploring!"
                    else "Continue"
                super.onPageSelected(position)
            }
        })

        // Handle next Button
        binding.btnNext.setOnClickListener {
            val current = binding.viewPager.currentItem
            if (current < pages.lastIndex) {
                binding.viewPager.currentItem = current + 1
            } else {
                // last page navigate to real main screen
                startActivity(Intent(this, HomeActivity::class.java))
                finish() // so user don't comeback to this screen
            }
        }

    }
}