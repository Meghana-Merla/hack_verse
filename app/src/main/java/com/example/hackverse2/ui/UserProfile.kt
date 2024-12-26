package com.example.hackverse2.ui

import ReadWriteUserDetails
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hackverse2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment : Fragment() {

    private lateinit var textViewWelcome: TextView
    private lateinit var textViewFullName: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var textViewDob: TextView
    private lateinit var textViewGender: TextView
    private lateinit var textViewMobile: TextView
    private lateinit var textViewEducation: TextView
    private lateinit var textViewAddress: TextView
    private lateinit var progressBar: ProgressBar

    private lateinit var authProfile: FirebaseAuth
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize views
        textViewWelcome = view.findViewById(R.id.textView_show_welcome)
        textViewFullName = view.findViewById(R.id.textView_show_full_name)
        textViewEmail = view.findViewById(R.id.textView_show_email)
        textViewDob = view.findViewById(R.id.textView_show_dob)
        textViewGender = view.findViewById(R.id.textView_show_gender)
        textViewMobile = view.findViewById(R.id.textView_show_mobile)
        textViewEducation = view.findViewById(R.id.textView_show_education)
        textViewAddress = view.findViewById(R.id.textView_show_address)
        progressBar = view.findViewById(R.id.progress_bar)

        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        authProfile = FirebaseAuth.getInstance()
        val firebaseUser = authProfile.currentUser

        // Observe loading and error states
        profileViewModel.isLoading.observe(viewLifecycleOwner) {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
        profileViewModel.error.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        if (firebaseUser == null) {
            profileViewModel.setError("Something went wrong! User's details are not available at the moment")
        } else {
            profileViewModel.setLoading(true)
            showUserProfile(firebaseUser)
        }

        return view
    }

    private fun showUserProfile(firebaseUser: FirebaseUser) {
        val userID = firebaseUser.uid
        val referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users")
        referenceProfile.child(userID).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val readUserDetails = snapshot.getValue(ReadWriteUserDetails::class.java)
                if (readUserDetails != null) {
                    val fullName = firebaseUser.displayName ?: "N/A"
                    val email = firebaseUser.email ?: "N/A"
                    val dob = readUserDetails.dob ?: "N/A"
                    val gender = readUserDetails.gender ?: "N/A"
                    val mobile = readUserDetails.mobile ?: "N/A"
                    val education = readUserDetails.education ?: "N/A"
                    val address = readUserDetails.address ?: "N/A"

                    textViewWelcome.text = "Welcome, $fullName!"
                    textViewFullName.text = fullName
                    textViewEmail.text = email
                    textViewDob.text = dob
                    textViewGender.text = gender
                    textViewMobile.text = mobile
                    textViewEducation.text = education
                    textViewAddress.text = address
                } else {
                    profileViewModel.setError("User details not found.")
                }
                profileViewModel.setLoading(false)
            }

            override fun onCancelled(error: DatabaseError) {
                profileViewModel.setError("Error: ${error.message}")
                profileViewModel.setLoading(false)
            }
        })
    }
}
