package com.example.bokoar

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import android.widget.TextView
import android.widget.ImageView


class HowToUseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_to_use)

        val toolbar = findViewById<MaterialToolbar>(R.id.topAppBarHowTo)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        setupSteps()

    }

    private fun configureStep(
        root: View,
        number: String,
        iconRes: Int,
        title: String,
        desc: String
    ) {
        val iconView = root.findViewById<ImageView>(R.id.iconCircle)
        val tvNumber = root.findViewById<TextView>(R.id.tvStepNumber)
        val tvTitle = root.findViewById<TextView>(R.id.tvStepTitle)
        val tvDesc = root.findViewById<TextView>(R.id.tvStepDesc)

        iconView.setImageResource(iconRes)
        tvNumber.text = number
        tvTitle.text = title
        tvDesc.text = desc
    }

    private fun setupSteps(){
        // Step 1
        val step1 = findViewById<View>(R.id.step1)
        configureStep(
            root = step1,
            number = "1",
            iconRes = R.drawable.cameraicon,
            title = "Allow camera access",
            desc = "Grant permission to use your device camera for AR features."
        )

        // Step 2
        val step2 = findViewById<View>(R.id.step2)
        configureStep(
            root = step2,
            number = "2",
            iconRes = R.drawable.cameraicon,
            title = "Choose a location",
            desc = "Select a destination from the map or list."
        )

        // Step 3

        val step3 = findViewById<View>(R.id.step3)
        configureStep(
            root = step3,
            number = "3",
            iconRes = R.drawable.cameraicon,
            title = "Point at a Marker",
            desc = "Aim your camera at the AR marker at the site."
        )

        // Step 4

        val step4 = findViewById<View>(R.id.step4)
        configureStep(
            root = step4,
            number = "4",
            iconRes = R.drawable.cameraicon,
            title = "View AR",
            desc = "Explore the sites!"
        )
    }
}