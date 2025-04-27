package com.ngio.easyhire.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ngio.easyhire.R

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }
    override fun onResume() {
        super.onResume()
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.colorPrimary)
    }

}