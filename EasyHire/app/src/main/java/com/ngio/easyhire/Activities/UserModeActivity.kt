package com.ngio.easyhire.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.ngio.easyhire.R

class UserModeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modousuario)

        val card_seller = findViewById<CardView>(R.id.card_seller)
        val card_user = findViewById<CardView>(R.id.card_user)
        val mainMenuActivity = Intent(this, MainMenuActivity::class.java)

        card_seller.setOnClickListener {
            startActivity(mainMenuActivity)
            this.finish()
        }
        card_user.setOnClickListener {
            startActivity(mainMenuActivity)
            this.finish()
        }

    }
}