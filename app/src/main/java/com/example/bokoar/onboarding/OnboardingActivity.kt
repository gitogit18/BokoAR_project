package com.example.bokoar.onboarding
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
                title = "Welcome to Boko AR",
                description = "Experience Ratu Boko through AR",
                iconRes = R.drawable.onboarding1
            ),
            OnboardingPage(
                title = "Explore With AR!",
                description = "Follow interactive maps to discover key areas of Ratu Boko without getting lost.",
                iconRes = R.drawable.onboarding3
            ),

            OnboardingPage(
                title = "Audio guide Available",
                description = "Listen and Read curated stories about the history and culture behind each spot",
                iconRes = R.drawable.onboarding4
            )
        )

        val adapter = OnboardingAdapter(pages)
        binding.viewPager.adapter = adapter
        // connects dots indicator to the ViewPager
        binding.dotsIndicator.attachTo(binding.viewPager)

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