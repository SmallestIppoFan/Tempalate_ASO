package com.example.teacher888.screens.MainScreen

import android.os.Bundle
import android.webkit.WebView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel

class WebViewViewModel : ViewModel() {
    var webView: WebView? = null
    var url: String = "https://teacher888.cfd/cVhC1TTy"

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        webView?.saveState(Bundle())
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(savedInstanceState: Bundle?) {
        webView?.restoreState(savedInstanceState!!)
    }
}


