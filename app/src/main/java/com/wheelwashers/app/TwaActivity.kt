package com.wheelwashers.app

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class TwaActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twa)

        // Hide system UI for immersive experience using modern API
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        webView = findViewById(R.id.webView)
        progressBar = findViewById(R.id.progressBar)

        // Configure WebView settings
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            setSupportZoom(true)
            builtInZoomControls = false
            loadWithOverviewMode = true
            useWideViewPort = true
        }

        // Set WebViewClient to handle page navigation
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE
            }
        }

        // Set WebChromeClient to handle progress
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                progressBar.progress = newProgress
            }
        }

        // Handle back button press
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    finish()
                }
            }
        })

        // Load the URL
        val url = intent.data?.toString() ?: getString(R.string.website_url)
        webView.loadUrl(url)
    }
}
