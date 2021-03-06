package com.tugaskita.ominel

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    private val mWaitHandler: Handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mWaitHandler.postDelayed({
            //The following code will execute after the 3 seconds.
            try { //Go to next page i.e, start the next activity.
                startActivity(Intent(applicationContext, MainActivity::class.java))
                //Let's Finish Splash Activity since we don't want to show this when user press back button.
                finish()
            } catch (ignored: Exception) {
                ignored.printStackTrace()
            }
        }, 3000) // Give a 3 seconds delay.
    }

    public override fun onDestroy() {
        super.onDestroy()
        //Remove all the callbacks otherwise navigation will execute even after activity is killed or closed.
        mWaitHandler.removeCallbacksAndMessages(null)
    }
}
