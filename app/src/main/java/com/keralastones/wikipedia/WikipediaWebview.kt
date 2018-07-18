package com.keralastones.wikipedia

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebViewFragment
import kotlinx.android.synthetic.main.fragment_web.loadingText
import kotlinx.android.synthetic.main.fragment_web.webview
import android.widget.Toast



class WikipediaWebview : WebViewFragment() {

    companion object {

        fun newInstance(loadingMessage: String, url : String) : WikipediaWebview{
            var argumentBundle = Bundle()
            var webViewFragment = WikipediaWebview()
            argumentBundle.putString("LOADING_MSG", loadingMessage)
            argumentBundle.putString("URL", url)
            webViewFragment.arguments = argumentBundle
            return webViewFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val root = inflater.inflate(R.layout.fragment_web, container, false)
        return root
    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        bundle?.let {
            val loadingMsg = bundle.getString("LOADING_MSG")
            val url = bundle.getString("URL")
            loadingText.visibility = View.VISIBLE
            loadingText.text = loadingMsg
            val customWebView = webview
            customWebView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)
                    loadingText.visibility = View.GONE
                }
            }
            customWebView.loadUrl(url)
        }
    }
}