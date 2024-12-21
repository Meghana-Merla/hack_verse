package com.example.hackverse2

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextLoginEmail: EditText
    private lateinit var editTextLoginPwd: EditText

    private lateinit var progressBar: ProgressBar

    private lateinit var authProfile: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        supportActionBar?.title = "Login"
        Toast.makeText(this, "You can Login now", Toast.LENGTH_LONG).show()

        editTextLoginEmail = findViewById(R.id.editText_login_email)
        editTextLoginPwd = findViewById(R.id.editText_login_pwd)

        progressBar = findViewById(R.id.progressBar)
        val button_login: Button = findViewById(R.id.button_login)

        // Initialize FirebaseAuth instance
        authProfile = FirebaseAuth.getInstance()

        button_login.setOnClickListener {

            val textEmail = editTextLoginEmail.text.toString().trim()
            val textPwd = editTextLoginPwd.text.toString().trim()

            if (textEmail.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                editTextLoginEmail.error = "Email is required"
                editTextLoginEmail.requestFocus()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                Toast.makeText(this, "Please re-enter your email", Toast.LENGTH_SHORT).show()
                editTextLoginEmail.error = "Valid email is required"
                editTextLoginEmail.requestFocus()
            } else if (textPwd.isEmpty()) {
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
                editTextLoginPwd.error = "Password is required"
                editTextLoginPwd.requestFocus()
            } else {
                progressBar.visibility = View.VISIBLE
                loginUser(textEmail, textPwd)
            }
        }
    }

    private fun loginUser(textEmail: String, textPwd: String) {
        authProfile.signInWithEmailAndPassword(textEmail, textPwd)
            .addOnCompleteListener(this) { task ->
                progressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@LoginActivity,
                        "You are logged in now",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Proceed to the next screen or activity
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Login failed! Please check your credentials and try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
}