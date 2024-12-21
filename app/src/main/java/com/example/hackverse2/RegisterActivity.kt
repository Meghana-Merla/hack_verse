package com.example.hackverse2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.common.logging.Logger
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextRegisterFullName: EditText
    private lateinit var editTextRegisterEmail: EditText
    private lateinit var editTextRegisterPwd: EditText
    private lateinit var editTextRegisterConfirmPwd: EditText

    private lateinit var progressBar: ProgressBar

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        supportActionBar?.title = "Register"
        Toast.makeText(this, "You can register now", Toast.LENGTH_LONG).show()

        editTextRegisterFullName = findViewById(R.id.editText_register_full_name)
        editTextRegisterEmail = findViewById(R.id.edittext_register_mail_id)
        editTextRegisterPwd = findViewById(R.id.editText_register_password)
        editTextRegisterConfirmPwd = findViewById(R.id.editText_register_confirmation)

        progressBar = findViewById(R.id.progress_bar)
        val register_button: Button = findViewById(R.id.register_button)

        //Register User using the credentials

        fun registerUser(textFullName: String, textEmail: String, textPwd: String) {
            val auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(textEmail, textPwd)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Registration successful
                        Log.d("RegisterActivity", "User registered: ${auth.currentUser?.uid}")
                        Toast.makeText(
                            this@RegisterActivity,
                            "User registered successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        val firebaseUser: FirebaseUser? = auth.currentUser

                        // enter user data to the firebase realtime database
                        




                        // Optionally save user data to Firestore
                    } else {
                        // Registration failed
                        Log.e("RegisterActivity", "Registration failed: ${task.exception?.message}")
                        Toast.makeText(
                            this@RegisterActivity,
                            "Registration failed: ${task.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    progressBar.visibility = View.GONE
                }
        }



        register_button.setOnClickListener {
            val textFullName = editTextRegisterFullName.text.toString()
            val textEmail = editTextRegisterEmail.text.toString()
            val textPwd = editTextRegisterPwd.text.toString()
            val textConfirmPwd = editTextRegisterConfirmPwd.text.toString()

            if (textFullName.isEmpty()) {
                editTextRegisterFullName.error = "Full Name is required"
                editTextRegisterFullName.requestFocus()
            } else if (textEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(textEmail)
                    .matches()
            ) {
                editTextRegisterEmail.error = "Valid email is required"
                editTextRegisterEmail.requestFocus()
            } else if (textPwd.isEmpty() || textPwd.length < 6) {
                editTextRegisterPwd.error = "Password must be at least 6 characters"
                editTextRegisterPwd.requestFocus()
            } else if (textPwd != textConfirmPwd) {
                editTextRegisterConfirmPwd.error = "Passwords do not match"
                editTextRegisterConfirmPwd.requestFocus()
            } else {
                progressBar.visibility = View.VISIBLE
                registerUser(textFullName, textEmail, textPwd)
            }
        }

    }

}
