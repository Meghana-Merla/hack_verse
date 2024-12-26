package com.example.hackverse2.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hackverse2.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        val progressBar: ProgressBar = binding.dashboardprogressBar

        // Observe the LiveData for text changes
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Observe the loading state to show/hide the progress bar
        dashboardViewModel.isLoading.observe(viewLifecycleOwner) {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        // Example of setting loading state before performing an action
        dashboardViewModel.setLoading(true)
        // Simulate fetching data, and then update UI
        // Simulate a delay for loading example
        root.postDelayed({
            dashboardViewModel.setLoading(false) // Set loading to false after data is loaded
            dashboardViewModel.updateText("Data loaded successfully!") // Update UI with new data
        }, 3000)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
