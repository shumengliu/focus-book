package com.honestboook.focusbook.fragments.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.honestboook.focusbook.SiteViewModel
import com.honestboook.focusbook.databinding.FragmentAddBinding
import android.text.TextUtils
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.honestboook.focusbook.R
import com.honestboook.focusbook.model.Site

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null

    // This property is only valid between on CreateView and onDestroyView
    private val binding get() = _binding!!

    private lateinit var siteViewModel: SiteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)

        siteViewModel = ViewModelProvider(this).get(SiteViewModel::class.java)

        binding.addBtn.setOnClickListener {
            insertDataToDataBase()
        }

        val view = binding.root
        return view
    }

    private fun insertDataToDataBase() {
        val url = binding.siteUrl.text.toString()

        if (inputCheck(url)) {
            val site = Site(0, url)
            siteViewModel.addSite(site)
            Toast.makeText(requireContext(), "Successfully added site!", Toast.LENGTH_LONG).show()

            // navigate back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out name and URL", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(url: String): Boolean {
        return !TextUtils.isEmpty(url)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}