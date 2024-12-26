package com.example.hackverse2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progress_bar)
        auth = FirebaseAuth.getInstance()

        progressBar.visibility = View.VISIBLE

        // Check if user is already authenticated
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is signed in, navigate to HomeActivity
            navigateToHomeActivity()
        } else {
            // Show login screen or a loading state
            navigateToLoginActivity()
        }
    }

    private fun navigateToHomeActivity() {
        progressBar.visibility = View.GONE
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToLoginActivity() {
        // After loading is done, hide progress bar
        progressBar.visibility = View.GONE
        // Navigate to login screen
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
