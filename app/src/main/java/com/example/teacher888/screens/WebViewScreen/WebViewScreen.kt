package com.example.teacher888.screens.WebViewScreen

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.teacher888.screens.MainScreen.UserStore

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen() {
    @Composable
    fun WebViewScreen() {
        val context = LocalContext.current
        var currentUrl by remember { mutableStateOf("") }
        val user = UserStore(context = context)
        val link = user.getLink.collectAsState(initial = "").value

        val webView = remember(LocalConfiguration.current) {
            WebView(context).apply {
                val cookieManager = CookieManager.getInstance()
                cookieManager.setAcceptCookie(true)

                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    cacheMode = WebSettings.LOAD_DEFAULT
                    setSupportZoom(true)
                    builtInZoomControls = true
                    displayZoomControls = false
                }

                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                        currentUrl = request?.url.toString()
                        return false
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        currentUrl = url.orEmpty()
                    }
                }

                webChromeClient = object : WebChromeClient() {
                    override fun onShowFileChooser(
                        webView: WebView?,
                        filePathCallback: ValueCallback<Array<Uri>>?,
                        fileChooserParams: FileChooserParams?
                    ): Boolean {
                        // You might need additional permissions to access files
                        return true
                    }
                }

                setOnKeyListener { v, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.action == MotionEvent.ACTION_UP
                        && canGoBack()
                    ) {
                        goBack()
                        true
                    } else false
                }
            }
        }

        // Always load the URL when link changes
        LaunchedEffect(link) {
            webView.loadUrl(link)
            Log.d("RARA", link)
        }

        Surface(modifier = Modifier.fillMaxSize()) {
            AndroidView({ webView })
        }
    }

}
