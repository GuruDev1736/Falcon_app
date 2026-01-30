package com.wheelwashers.app

import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.button.MaterialButton

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // Get all views
        val logo: ImageView = findViewById(R.id.iv_logo)
        val title: TextView = findViewById(R.id.tv_welcome_title)
        val subtitle: TextView = findViewById(R.id.tv_welcome_subtitle)
        val plumbingCard: CardView = findViewById(R.id.card_plumbing)
        val acCard: CardView = findViewById(R.id.card_ac)
        val featureBadge: CardView = findViewById(R.id.feature_badge)
        val letsGoButton: MaterialButton = findViewById(R.id.btn_lets_go)

        // Apply entrance animations
        animateViews(logo, title, subtitle, plumbingCard, acCard, featureBadge, letsGoButton)

        // Button click with animation
        letsGoButton.setOnClickListener {
            animateButtonClick(it) {
                openTrustedWebActivity()
            }
        }

        // Add floating animation to logo
        startFloatingAnimation(logo)
    }

    private fun animateViews(
        logo: View,
        title: View,
        subtitle: View,
        plumbingCard: View,
        acCard: View,
        featureBadge: View,
        button: View
    ) {
        // Logo animation - fade in and scale
        val logoAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in_scale)
        logo.startAnimation(logoAnim)

        // Title animation - slide up and fade in
        val titleAnim = AnimationUtils.loadAnimation(this, R.anim.slide_up_fade_in)
        title.startAnimation(titleAnim)

        // Subtitle animation - slide up and fade in (delayed)
        val subtitleAnim = AnimationUtils.loadAnimation(this, R.anim.slide_up_fade_in_delayed)
        subtitle.startAnimation(subtitleAnim)

        // Cards animation with stagger
        plumbingCard.alpha = 0f
        plumbingCard.translationX = -100f
        plumbingCard.animate()
            .alpha(1f)
            .translationX(0f)
            .setDuration(600)
            .setStartDelay(600)
            .setInterpolator(DecelerateInterpolator())
            .start()

        acCard.alpha = 0f
        acCard.translationX = 100f
        acCard.animate()
            .alpha(1f)
            .translationX(0f)
            .setDuration(600)
            .setStartDelay(700)
            .setInterpolator(DecelerateInterpolator())
            .start()

        // Feature badge animation
        featureBadge.alpha = 0f
        featureBadge.scaleX = 0.8f
        featureBadge.scaleY = 0.8f
        featureBadge.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(500)
            .setStartDelay(900)
            .setInterpolator(DecelerateInterpolator())
            .start()

        // Button animation - entrance from bottom
        val buttonAnim = AnimationUtils.loadAnimation(this, R.anim.button_entrance)
        button.startAnimation(buttonAnim)
    }

    private fun startFloatingAnimation(view: View) {
        // Create subtle floating effect for logo
        view.postDelayed({
            val animator = ObjectAnimator.ofFloat(view, "translationY", 0f, -20f, 0f)
            animator.duration = 3000
            animator.repeatCount = ObjectAnimator.INFINITE
            animator.interpolator = DecelerateInterpolator()
            animator.start()
        }, 1000)
    }

    private fun animateButtonClick(view: View, action: () -> Unit) {
        // Scale down animation on click
        view.animate()
            .scaleX(0.95f)
            .scaleY(0.95f)
            .setDuration(100)
            .withEndAction {
                // Scale back up
                view.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(100)
                    .withEndAction {
                        action()
                    }
                    .start()
            }
            .start()
    }

    private fun openTrustedWebActivity() {
        val url = getString(R.string.website_url)
        val intent = Intent(this, TwaActivity::class.java).apply {
            data = Uri.parse(url)
        }
        startActivity(intent)
        finish()
    }
}
