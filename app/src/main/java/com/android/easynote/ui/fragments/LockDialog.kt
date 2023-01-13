package com.android.easynote.ui.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.android.easynote.R
import com.android.easynote.core.extention.navigateSafe
import com.android.easynote.core.extention.popBack
import com.android.easynote.core.extention.popUpCurrentFragment
import com.android.easynote.core.extention.toast
import com.android.easynote.databinding.PopupLockLayoutBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class LockDialog : DialogFragment()  {
    private var _binding: PopupLockLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var sLockCode:String
    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = PopupLockLayoutBinding.inflate(LayoutInflater.from(requireContext()))
        return MaterialAlertDialogBuilder(requireContext())
            .setBackground(ColorDrawable(Color.TRANSPARENT))
            .setView(binding.root)
            .setCancelable(true)
            .create()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lockBtn.setOnClickListener {
                val code = codeLock.text
                if (code!!.length < 3) {
                    toast("please enter fully lock code")
                } else {
                    sLockCode = code.toString()
                    val navController = findNavController()
                    navController.previousBackStackEntry?.savedStateHandle?.set("key", sLockCode)
                    navController.popBackStack()
//                    navigateSafe(R.id.createNoteFragment, bundle,popUpCurrentFragment())
                }
            }

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }




}