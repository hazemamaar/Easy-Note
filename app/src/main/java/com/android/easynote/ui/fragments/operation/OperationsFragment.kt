package com.android.easynote.ui.fragments.operation


import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.easynote.R
import com.android.easynote.core.base.BaseFragment
import com.android.easynote.core.extention.*
import com.android.easynote.data.entities.NoteDto
import com.android.easynote.databinding.FragmentCreateNoteBinding
import com.android.easynote.ui.adapter.ColorAdapter
import com.android.easynote.utils.Constant.BACKGROUND_CARD_COLOR
import com.android.easynote.utils.Constant.PIN
import com.android.easynote.utils.Constant.READ_STORAGE_PERM
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


@Suppress("CAST_NEVER_SUCCEEDS")
class OperationsFragment : BaseFragment<FragmentCreateNoteBinding, OperationsViewModel>(),
    EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks {
    private lateinit var colorAdapter: ColorAdapter
    private var color: String? = BACKGROUND_CARD_COLOR
    private var pinValue: Int = PIN
    override val mViewModel: OperationsViewModel
            by inject()

    // Permission Private Read & Write

    private var selectedImagePath = ""
    private lateinit var navController: NavController
    private val args: OperationsFragmentArgs by navArgs()
    private var lockCode: String = ""
    lateinit var note: NoteDto

    @SuppressLint("ResourceType")
    override fun onFragmentReady() {
        navController = findNavController()
        setUpRv()
        choiceColor()
        getLockCode()
        doneEditOrCreate()
        onEditNote()
        binding.imageSelect.setOnClickListener {
            readStorageTask()
        }

        binding.pin.setOnClickListener {
            pinValue = 1
        }
        binding.apply {

            lock.setOnClickListener {
                navigateSafe(OperationsFragmentDirections.actionCreateNoteFragmentToLockDialog())
            }
            back.setOnClickListener {
                popBack()
            }
        }
    }

    private fun onEditNote() {
        if (args.note.id != 0) {
            binding.title.setText(args.note.title)
            binding.notesDescription.setText(args.note.noteText)
            binding.containerConstraints.setBackgroundColor(Color.parseColor(args.note.color.toString()))
            color = args.note.color
            pinValue = args.note.pin
            lockCode = args.note.lock.toString()
            binding.imageSelect.setImageURI(args.note.imgPath?.toUri())
        }
    }

    private fun setUpRv() = binding.apply {
        colorAdapter = ColorAdapter(requireContext())
        choiceColorRv.setHasFixedSize(true)
        choiceColorRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        choiceColorRv.adapter = colorAdapter
    }

    private fun choiceColor() = colorAdapter.setOnItemClickListener {
        color = it
        binding.containerConstraints.setBackgroundColor(Color.parseColor(color.toString()))
    }

    private fun getLockCode() =
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("key")
            ?.observe(viewLifecycleOwner) {
                lockCode = it
            }

    //    private fun checkEditOrCreate() :Boolean{
//
//
//    }
    @SuppressLint("SuspiciousIndentation")
    private fun doneEditOrCreate() = binding.done.setOnClickListener {
        val note = NoteDto(
            title = binding.title.text.toString(),
            color = color.toString(),
            pin = pinValue,
            noteText = binding.notesDescription.text.toString(),
            imgPath = selectedImagePath,
        )
        if (args.note.id != 0) {
            var notes =note.copy(id = args.note.id)
            if(lockCode.length < 2 || lockCode == "null") {
                notes = notes.copy( lock = args.note.lock)
            }else{
                notes = notes.copy( lock = lockCode)
            }
            mViewModel.editeNote(notes)
        } else {
            mViewModel.createNote(note)
        }
        observeOn()
    }
    private fun observeOn(){
        observe(mViewModel.viewState){ action ->
            when(action) {
                is OperationsAction.EditNote -> if(action.editId>= 1) snackBar("EditDone")
                is OperationsAction.CreateNote -> if(action.insertId>= 1) snackBar("AddDone")
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun hasReadStoragePerm(): Boolean {
        return EasyPermissions.hasPermissions(
            requireContext(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA
        )
    }

    private fun readStorageTask() {
        if (hasReadStoragePerm()) {

            pickImageFromGallery()
        } else {
            EasyPermissions.requestPermissions(
                requireActivity(),
                getString(R.string.storage_permission_text),
                READ_STORAGE_PERM,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    }

    private fun pickImageFromGallery() {
        ImagePicker.with(this)
            .compress(1024)         //Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080,
                1080)  //Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                registerResult.launch(intent)
            }
    }



    private val registerResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.data != null) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val fileUri = it.data?.data
                    selectedImagePath = fileUri.toString()
                    binding.imageSelect.setImageURI(fileUri)
                }
            }
        }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        EasyPermissions.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults,
            requireActivity()
        )
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(requireActivity(), perms)) {
            AppSettingsDialog.Builder(requireActivity()).build().show()
        }
    }

    override fun onRationaleAccepted(requestCode: Int) {
        TODO("Not yet implemented")
    }

    override fun onRationaleDenied(requestCode: Int) {
        TODO("Not yet implemented")
    }
}