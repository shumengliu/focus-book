package com.honestboook.focusbook.fragments.list

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.honestboook.focusbook.R
import com.honestboook.focusbook.SiteViewModel
import com.honestboook.focusbook.databinding.FragmentListBinding


class ListFragment : Fragment() {
    private val packageName = "com.honestboook.focusbook"
    private var _binding: FragmentListBinding? = null

    // This property is only valid between on CreateView and onDestroyView
    private val binding get() = _binding!!

    private lateinit var siteViewModel: SiteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)

        // RecyclerView setup
        val adapter = RecyclerAdapter()
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // SiteViewModel
        siteViewModel = ViewModelProvider(this)[SiteViewModel::class.java]
        siteViewModel.allSites.observe(viewLifecycleOwner, Observer { sites ->
            adapter.setData(sites)
        })

        binding.addFloatingBtn.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> deleteAllSites()
            R.id.menu_accessibility -> openAccessibilitySetting()
            R.id.menu_overlay -> openOverlaySetting()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openAccessibilitySetting() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        startActivity(intent)
    }

    private fun openOverlaySetting() {
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
        startActivity(intent)
    }

    private fun deleteAllSites() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            siteViewModel.deleteAllSites()
            Toast.makeText(
                requireContext(),
                "Successfully removed all sites from blocked list",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete all sites")
        builder.setMessage("Are you sure you want to remove all sites from the blocked sites?")
        builder.create().show()
    }
}