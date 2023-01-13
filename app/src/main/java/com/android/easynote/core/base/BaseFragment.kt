package com.android.easynote.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.android.easynote.core.extention.bindView


abstract class BaseFragment <B : ViewBinding, VM : ViewModel> : Fragment() {

    abstract fun onFragmentReady()

    protected abstract val mViewModel: VM

    private var _binding: B? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindView()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onFragmentReady()
    }
    override fun onDestroyView() {
        _binding=null
        super.onDestroyView()
    }

//    fun pickImage(
//        permissionManager: PermissionManager,
//        permissionLauncher: ActivityResultLauncher<Array<String>>?,
//        launcher: ActivityResultLauncher<Intent>
//    ) {
//        if (permissionManager.hasAllFilePickerPermissions()) {
//            FileManager.pickSingleImage(this, launcher)
//        } else {
//            permissionLauncher?.launch(permissionManager.getAllImagePermissions())
//        }
//    }

}
