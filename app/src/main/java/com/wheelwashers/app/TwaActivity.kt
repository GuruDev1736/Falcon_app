package com.wheelwashers.app

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat

class TwaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get URL from intent or use default
        val url = intent.data?.toString() ?: getString(R.string.website_url)

        // Launch Chrome Custom Tab (TWA)
        launchCustomTab(url)

        // Finish this activity since we're opening in browser
        finish()
    }

    private fun launchCustomTab(url: String) {
        val customTabsIntent = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .setUrlBarHidingEnabled(false)
            .setDefaultColorSchemeParams(
                androidx.browser.customtabs.CustomTabColorSchemeParams.Builder()
                    .setToolbarColor(ContextCompat.getColor(this, R.color.blue_primary))
                    .build()
            )
            .setShareState(CustomTabsIntent.SHARE_STATE_ON)
            .build()

        customTabsIntent.launchUrl(this, Uri.parse(url))
    }
}
