package com.falcon.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent

class TwaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val url = intent.data?.toString() ?: getString(R.string.website_url)
        launchTWA(url)
    }

    private fun launchTWA(url: String) {
        val builder = CustomTabsIntent.Builder()
        builder.setShowTitle(false)
        builder.setUrlBarHidingEnabled(true)

        val customTabsIntent = builder.build()
        customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        customTabsIntent.launchUrl(this, Uri.parse(url))
        finish()
    }
}
