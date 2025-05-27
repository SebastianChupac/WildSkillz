package com.example.wildskillz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LearnRegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_learn_registration)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.learn_registration)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val registerLearnButton = findViewById<Button>(R.id.btRegisterLearn)
        val usernameEdit = findViewById<EditText>(R.id.etUsername)
        val agreeTermsCheckbox = findViewById<CheckBox>(R.id.cbAgreeTerms)

        registerLearnButton.setOnClickListener {
            val username = usernameEdit.text.toString().trim()
            val agreed = agreeTermsCheckbox.isChecked

            if (username.isEmpty()) {
                usernameEdit.error = "Please enter a username"
                return@setOnClickListener
            }

            if (!agreed) {
                agreeTermsCheckbox.error = "You must agree to the terms"
                return@setOnClickListener
            }

            // If both are valid, save username
            val savedUsername = username
            // pass to next screen

            val intent = Intent(this, LearnHomeActivity::class.java)
            intent.putExtra("username", savedUsername)
            startActivity(intent)
        }
    }
}
