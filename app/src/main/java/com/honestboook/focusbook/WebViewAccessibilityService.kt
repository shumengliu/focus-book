package com.honestboook.focusbook

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.honestboook.focusbook.data.SiteDao
import com.honestboook.focusbook.data.SiteDatabase
import com.honestboook.focusbook.repository.SiteRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class WebViewAccessibilityService : AccessibilityService() {
    private val TAG: String = "WebViewAccessibilityService"
    private var prevApp: String = ""
    private var prevUrl: String = ""
//    private val siteDao: SiteDao = SiteDatabase.getDatabase(this).siteDao()
    private lateinit var siteRepository: SiteRepository

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        when (event?.eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                // triggers twice when shifting apps
                // triggers once when editing address bar
                // triggers another time when redirecting to another website
                resolveWindowStateChangeEvent(event)
            }
            AccessibilityEvent.TYPE_WINDOWS_CHANGED -> {
            }
            AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED -> {
                // a lot of events are of this type
                resolveWindowContentChangeEvent(event)
            }
            else -> {
//                Log.d(TAG, "Event with unexpected type detected")
            }
        }
    }

    private fun resolveWindowStateChangeEvent(event: AccessibilityEvent) {
    }

    private fun resolveWindowContentChangeEvent(event: AccessibilityEvent) {
        val parentNodeInfo: AccessibilityNodeInfo = event.source ?: return

        val packageName: String = event.packageName.toString()
        prevApp = packageName

        // Check if the user is using a browser
        // Exit if it is not a supported browser
        val browserConfig: SupportedBrowserConfig = getBrowserConfig(packageName) ?: return

        val capturedUrl: String? = captureUrl(parentNodeInfo, browserConfig)
        parentNodeInfo.recycle()

        if (capturedUrl == null || capturedUrl == "") {
            return
        }

        // check if the browser or url has changed since last time
        // to avoid repetitive logs
        if (packageName != prevApp || capturedUrl != prevUrl) {
            if (android.util.Patterns.WEB_URL.matcher(capturedUrl).matches()) {
//                Log.d("history", "URL is $capturedUrl")
                if (shouldBeBlocked(capturedUrl))
                redirectToBlankPage()
            }
            prevUrl = capturedUrl
        }
    }



    private fun getBrowserConfig(packageName: String): SupportedBrowserConfig? {
        for (supportedConfig in SupportedBrowserConfig.get()) {
            if (supportedConfig.packageName == packageName) {
                return supportedConfig
            }
        }
        return null
    }

    private fun captureUrl(info: AccessibilityNodeInfo, config: SupportedBrowserConfig): String? {
        val nodes: List<AccessibilityNodeInfo> =
            info.findAccessibilityNodeInfosByViewId(config.addressBarId)
        if (nodes.isEmpty()) {
            return null
        }

        val addressBarInfo: AccessibilityNodeInfo = nodes[0]
        return if (addressBarInfo.text != null) {
            addressBarInfo.text.toString()
        } else {
            null
        }
    }

    private fun shouldBeBlocked(capturedUrl: String): Boolean {
        var flag = false
        runBlocking {
            launch {
                for (prohibited in getBlockList()) {
                    if (capturedUrl.contains(prohibited, ignoreCase = true)) {
                        flag = true
                    }
                }
            }
        }
        return flag
    }

    private suspend fun getBlockList(): List<String> {
        return siteRepository.getBlockList()
    }

    private fun redirectToBlankPage() {
        Log.d(TAG, "redirecting to blank page")
        val blankPage: Uri = Uri.parse("about:blank")
        val intent = Intent(Intent.ACTION_VIEW, blankPage)
        intent.flags= Intent.FLAG_ACTIVITY_NEW_TASK
        applicationContext.startActivity(intent)
    }

    override fun onInterrupt() {
    }

    override fun onCreate() {
        super.onCreate()
        val siteDao = SiteDatabase.getDatabase(this).siteDao()
        siteRepository = SiteRepository(siteDao)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    class SupportedBrowserConfig(val packageName: String, val addressBarId: String) {
        companion object SupportedBrowsers {
            fun get(): List<SupportedBrowserConfig> {
                return listOf(
                    SupportedBrowserConfig("com.android.chrome", "com.android.chrome:id/url_bar"),
                    SupportedBrowserConfig(
                        "org.mozilla.firefox",
                        "org.mozilla.firefox:id/mozac_browser_toolbar_url_view"
                    ),
                    SupportedBrowserConfig("com.opera.browser", "com.opera.browser:id/url_field"),
                    SupportedBrowserConfig(
                        "com.opera.mini.native",
                        "com.opera.mini.native:id/url_field"
                    ),
                    SupportedBrowserConfig(
                        "com.duckduckgo.mobile.android",
                        "com.duckduckgo.mobile.android:id/omnibarTextInput"
                    ),
                    SupportedBrowserConfig("com.microsoft.emmx", "com.microsoft.emmx:id/url_bar"),
                )
            }
        }
    }

}