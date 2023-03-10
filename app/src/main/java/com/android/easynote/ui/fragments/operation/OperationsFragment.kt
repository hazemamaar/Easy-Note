package com.android.easynote.ui.fragments.operation


import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.easynote.core.base.BaseFragment
import com.android.easynote.core.extention.*
import com.android.easynote.data.entities.NoteDto
import com.android.easynote.databinding.FragmentCreateNoteBinding
import com.android.easynote.ui.adapter.ColorAdapter
import com.android.easynote.utils.Constant.BACKGROUND_CARD_COLOR
import com.android.easynote.utils.Constant.PIN
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.fragment_create_note.*
import org.koin.android.ext.android.inject
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


@Suppress("CAST_NEVER_SUCCEEDS")
class OperationsFragment : BaseFragment<FragmentCreateNoteBinding, OperationsViewModel>(),
    EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks {
    private val colorAdapter: ColorAdapter by inject()
    private var color: String? = BACKGROUND_CARD_COLOR
    private var pinValue: Int = PIN
    override val mViewModel: OperationsViewModel
            by inject()

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
        binding.addImageFab.setOnClickListener {
            pickImageFromGallery()
        }
        binding.pin.setOnClickListener {
            pinValue = if(pinValue == 1){
                toast("this note not pinned")
                0
            }else {
                toast("this note pinned")
                1
            }
        }
        binding.apply {

            lock.setOnClickListener {
                navigateSafe(OperationsFragmentDirections.actionCreateNoteFragmentToLockDialog(false))
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
            if (args.note.imgPath != null)
                binding.imageSelect.visible()
        }
    }

    private fun setUpRv() = binding.apply {
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

    @SuppressLint("SuspiciousIndentation")
    private fun doneEditOrCreate() = binding.done.setOnClickListener {
        val note = NoteDto(
            title = binding.title.text.toString(),
            color = color.toString(),
            pin = pinValue,
            lock = lockCode,
            noteText = binding.notesDescription.text.toString(),
            imgPath = selectedImagePath
        )
        if (args.note.id != 0) {
            val notes = note.copy(id = args.note.id)
            mViewModel.editeNote(notes)
        } else {
            mViewModel.createNote(note)
        }
        observeOn()
    }

    private fun observeOn() {
        mViewModel.apply {
            observe(mViewModel.viewState) { action ->
                handleUiState(action)
            }
        }
    }

    private fun handleUiState(action: OperationsAction) {
        when (action) {
            is OperationsAction.EditNote -> if (action.editId >= 1) {
                snackBar("EditDone")
                popBack()}
            is OperationsAction.CreateNote -> if (action.insertId >= 1) {snackBar("AddDone")
                popBack()}
        }
    }

//    private fun hasReadStoragePerm(): Boolean {
//        return EasyPermissions.hasPermissions(
//            requireContext(),
//            android.Manifest.permission.READ_EXTERNAL_STORAGE,
//            android.Manifest.permission.CAMERA,
//            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            android.Manifest.permission.ACCESS_COARSE_LOCATION
//        )
//    }


    private fun pickImageFromGallery() {
        ImagePicker.with(this)
            .compress(1024)
            .maxResultSize(
                1080,
                1080
            )
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
                    binding.imageSelect.visible()
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