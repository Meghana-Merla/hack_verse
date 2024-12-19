package com.example.hackverse2

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextRegisterFullName: EditText
    private lateinit var editTextRegisterEmail: EditText
    private lateinit var editTextRegisterPwd: EditText
    private lateinit var editTextRegisterConfirmPwd: EditText

    private lateinit var progressBar: ProgressBar


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

        //Register User using the credentials

        fun registerUser(textFullName: String, textEmail: String, textPwd: String) {
            val auth = FirebaseAuth.getInstance()

            auth.createUserWithEmailAndPassword(textEmail, textPwd)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this@RegisterActivity,
                            "User registered successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        val firebaseUser = auth.currentUser

                        // Send Verification Email
                        firebaseUser?.sendEmailVerification()?.addOnCompleteListener { emailTask ->
                            if (emailTask.isSuccessful) {
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "Verification email sent!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "Failed to send verification email: ${emailTask.exception?.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

//                        // Open User Profile after successful registration
//                        val intent = Intent(this@RegisterActivity, UserProfileActivity::class.java)
//                        //To prevent User from returning back to register activity on pressing back button after registration
//                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
//                                Intent.FLAG_ACTIVITY_CLEAR_TASK or
//                                Intent.FLAG_ACTIVITY_NEW_TASK
//                        startActivity(intent)
//                        finish() // Close the current activity


                    }
                }
        }

        fun onClick(v: View) {

            // Obtain the entered data
            val textFullName = editTextRegisterFullName.text.toString()
            val textEmail = editTextRegisterEmail.text.toString()
            val textPwd = editTextRegisterPwd.text.toString()
            val textConfirmPwd = editTextRegisterConfirmPwd.text.toString()


            // Can't obtain the value before verifying if any button was selected or not
            if (textFullName.isEmpty()) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Please enter your full name",
                    Toast.LENGTH_LONG
                ).show()
                editTextRegisterFullName.error = "Full Name is required"
                editTextRegisterFullName.requestFocus()
            } else if (textEmail.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_LONG).show()
                editTextRegisterEmail.error = "Email is required"
                editTextRegisterEmail.requestFocus()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                Toast.makeText(this, "Please re-enter your email", Toast.LENGTH_LONG).show()
                editTextRegisterEmail.error = "Valid email is required"
                editTextRegisterEmail.requestFocus()
            } else if (textPwd.isEmpty()) {
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_LONG).show()
                editTextRegisterPwd.error = "Password is required"
                editTextRegisterPwd.requestFocus()
            } else if (textPwd.length < 6) {
                Toast.makeText(this, "Password should be at least 6 digits", Toast.LENGTH_LONG)
                    .show()
                editTextRegisterPwd.error = "Password too weak"
                editTextRegisterPwd.requestFocus()
            } else if (textConfirmPwd.isEmpty()) {
                Toast.makeText(this, "Please confirm your password", Toast.LENGTH_LONG).show()
                editTextRegisterConfirmPwd.error = "Password Confirmation is required"
                editTextRegisterConfirmPwd.requestFocus()
            } else if (textPwd == textConfirmPwd) {
                Toast.makeText(this, "Please same same password", Toast.LENGTH_LONG).show()
                editTextRegisterConfirmPwd.error = "Password Confirmation is required"
                editTextRegisterConfirmPwd.requestFocus()

                // Clear the entered passwords
                editTextRegisterPwd.text.clear()
                editTextRegisterConfirmPwd.text.clear()
            } else {
                progressBar.setVisibility(View.VISIBLE)
                registerUser(textFullName, textEmail, textPwd)


            }
        }


    };
    }
