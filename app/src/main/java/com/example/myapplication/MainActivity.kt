package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar


class MainActivity : AppCompatActivity() {

    lateinit var webView:WebView
    var url=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()

        webView=findViewById<WebView>(R.id.webView)

        //dodato-----------------------------------
        webView.settings.loadWithOverviewMode=true
        webView.settings.useWideViewPort=true
        val scale =intent.getStringExtra("scale")
        val brightness=intent.getStringExtra("brightness")
        val startTime=intent.getStringExtra("startTime")
        val endTime=intent.getStringExtra("endTime")

        //brightness-----------------------------------------
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        val params=window.attributes
        params.screenBrightness=brightness!!.toFloat()/100f
        window.attributes=params
        var isOn:Boolean=true


        val startT =LocalTime.parse(startTime, DateTimeFormatter.ISO_LOCAL_TIME)
        val endT =LocalTime.parse(endTime, DateTimeFormatter.ISO_LOCAL_TIME)


        val handler= Handler()

        handler.postDelayed(object: Runnable{
            override fun run() {

                val currentTime : LocalTime=LocalTime.now()

                if(currentTime.isAfter(startT) && currentTime.isBefore(endT))
                {
                    val params=window.attributes
                    params.screenBrightness=brightness!!.toFloat()/100f
                    window.attributes=params
                    isOn=true
                }
                else
                {
                    val params=window.attributes
                    params.screenBrightness=0.0f
                    window.attributes=params
                    isOn=false
                }
                handler.postDelayed(this,30000)
            }
        },30000)



        //Toast.makeText(this,scale+" "+brightness+" "+startTime+" "+endTime,Toast.LENGTH_LONG).show()
        webView.setInitialScale(scale!!.toInt())
        val webSettings:WebSettings=webView.settings
        webSettings.javaScriptEnabled=true
        webSettings.domStorageEnabled=true
        webSettings.mediaPlaybackRequiresUserGesture=false

        webView.webViewClient= WebViewClient()
       // webSettings.set
        //-------------------------------------
        val str=intent.getStringExtra("url")


        webView.loadUrl(str!!)

        }

    override fun onStop() {

        super.onStop()
    }
}
