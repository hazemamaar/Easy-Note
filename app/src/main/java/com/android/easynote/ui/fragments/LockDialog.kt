package com.android.easynote.ui.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.android.easynote.R
import com.android.easynote.core.extention.navigateSafe
import com.android.easynote.core.extention.toast
import com.android.easynote.databinding.PopupLockLayoutBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class LockDialog : DialogFragment() {
    private var _binding: PopupLockLayoutBinding? = null
    private val binding get() = _binding!!

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
                    val bundle = Bundle()
                    bundle.putString("lockCode", code.toString())
                    navigateSafe(R.id.action_lockDialog_to_createNoteFragment, bundle)
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