package com.tugaskita.ominel

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    // Default is Google Search Engine
    var sev = "https://google.com/search?q="
    var keyword = ""

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enabling JavaScript from webview
        web.settings.javaScriptEnabled = true
        // Buat webview tidak redirect ke default browser, tetap di dalam aplikasi
        web.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url)
                return false // then it is not handled by default action
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                // Hide Progress Bar
                progressBar.visibility = View.INVISIBLE
                super.onPageFinished(view, url)
            }
        }

        searchbox.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                // Load Every Word Typed & Save the value to 'keyword' global variable
                keyword = "$s"
                web.loadUrl("${sev.toLowerCase(Locale.US)}$keyword")
                // Show Progress Bar
                progressBar.visibility = View.VISIBLE
            }
        })
    }

    fun selectSearchEngineDialog(view: View) {
        val items = arrayOf("Google", "DuckDuckGo", "Bing", "Yahoo")
        val itemIcons =
            arrayOf(R.drawable.google, R.drawable.duckduckgo, R.drawable.bing, R.drawable.yahoo)
        val itemUrls = arrayOf(
            "https://google.com/search?q=",
            "https://duckduckgo.com/?q=",
            "https://www.bing.com/search?q=",
            "https://search.yahoo.com/search?p="
        )
        with(AlertDialog.Builder(this))
        {
            setTitle("Select the search engine that you want")
            setItems(items) { dialog, index ->
                // Update sev (Search Engine Value) global variable value
                sev = itemUrls[index]

                // Change the search engine icon
                searchengine.setImageResource(itemIcons[index])

                // Reload webview after search engine selection has been made
                web.loadUrl("${sev.toLowerCase(Locale.US)}$keyword")

                // Toasted for user changes
                Toast.makeText(
                    applicationContext,
                    "${items[index]}'s search engine has been chosen",
                    Toast.LENGTH_SHORT
                ).show()
            }

            setPositiveButton("OK", null)
            show()
        }
    }

    override fun onBackPressed() {
        if (web.canGoBack())
            web.goBack()
        else
            finish()
    }
}
