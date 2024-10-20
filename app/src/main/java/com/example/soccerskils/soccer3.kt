package com.example.soccerskils

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class soccer3 : AppCompatActivity() {
    lateinit var name: TextView
    override fun onCreate(savedInstanseState: Bundle?) {
        super.onCreate(savedInstanseState)
        setContentView(R.layout.activity_soccer3)
        name = findViewById(R.id.textView10)
        name.setText(name.getText().toString() + " " + intent.getStringExtra("name"))

    }
    fun back(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}