package com.example.hackverse2

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        progressBar = findViewById(R.id.progress_bar)
        progressBar.visibility = View.VISIBLE

        // Simulate loading data
        loadData()
    }

    private fun loadData() {
        // Simulate a network call or database fetch
        Thread {
            try {
                Thread.sleep(2000) // Simulate network delay
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            runOnUiThread {
                progressBar.visibility = View.GONE
                // Proceed with setting up the UI with data
                // Example: Set data to RecyclerView, etc.
            }
        }.start()
    }
}
