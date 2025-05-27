package com.example.wildskillz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val learnButton = findViewById<Button>(R.id.btLearn)
        val teachButton = findViewById<Button>(R.id.btTeach)

        learnButton.setOnClickListener {
            val intent = Intent(this, LearnRegistrationActivity::class.java)
            startActivity(intent)
        }

        teachButton.setOnClickListener {
            val intent = Intent(this, TeachRegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}