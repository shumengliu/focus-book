package com.honestboook.focusbook.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.honestboook.focusbook.R
import com.honestboook.focusbook.SiteViewModel
import com.honestboook.focusbook.databinding.FragmentUpdateBinding
import com.honestboook.focusbook.model.Site


class UpdateFragment : Fragment() {
    private val args by navArgs<UpdateFragmentArgs>()

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private lateinit var siteViewModel: SiteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)

        siteViewModel = ViewModelProvider(this).get(SiteViewModel::class.java)

        binding.updateSiteUrl.setText(args.currentSite.url)

        binding.updateBtn.setOnClickListener {
            updateSite()
        }

        // Add delete menu
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun updateSite() {
        val url = binding.updateSiteUrl.text.toString()

        if (inputCheck(url)) {
            // create new site
            // IMPORTANT: use id of current site
            val updatedSite = Site(args.currentSite.id, url)
            // update current site
            siteViewModel.updateSite(updatedSite)
            Toast.makeText(requireContext(), "Site update success", Toast.LENGTH_SHORT).show()
            // navigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out name and url", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(url: String): Boolean {
        return !TextUtils.isEmpty(url)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteSite()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteSite() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            siteViewModel.deleteSite(args.currentSite)
            Toast.makeText(requireContext(), "Successfully removed ${args.currentSite.url}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentSite.url}")
        builder.setMessage("Are you sure you want to remove ${args.currentSite.url} from the blocked sites?")
        builder.create().show()
    }
}