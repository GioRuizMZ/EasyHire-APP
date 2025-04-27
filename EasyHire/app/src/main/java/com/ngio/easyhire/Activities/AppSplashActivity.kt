package com.ngio.easyhire.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.facebook.AccessToken
import com.google.firebase.auth.FirebaseAuth
import com.ngio.easyhire.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AppSplashActivity : AppCompatActivity() {
    private val auth = FirebaseAuth.getInstance().currentUser
    private val accessToken = AccessToken.getCurrentAccessToken()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        val logo = findViewById<ImageView>(R.id.splashLogo)
        val marca = findViewById<TextView>(R.id.marca)

        logo.alpha = 0f
        logo.translationY = 50f
        marca.alpha = 0f
        marca.translationY = -50f

        marca.animate()
            .alpha(1.0f)
            .translationY(0f).duration = 500
        logo.animate()
            .alpha(1.0f)
            .translationY(0f)
            .setDuration(500)
            .withEndAction {
                lifecycleScope.launch {
                    delay(1500)
                    if (auth != null || accessToken != null) {
                        startActivity(Intent(this@AppSplashActivity, MainMenuActivity::class.java))
                        finish()
                    } else {
                        startActivity(Intent(this@AppSplashActivity, LoginActivity::class.java))
                        finish()
                    }
                }
            }
    }

}