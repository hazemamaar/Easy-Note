package com.android.easynote.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.easynote.R
import com.android.easynote.core.base.BaseFragment
import com.android.easynote.core.extention.navigateSafe
import com.android.easynote.core.extention.snackBar
import com.android.easynote.data.entities.NoteDto
import com.android.easynote.databinding.FragmentCreateNoteBinding
import com.android.easynote.ui.adapter.ColorAdapter
import com.android.easynote.utils.Constant.BACKGROUND_CARD_COLOR
import com.android.easynote.utils.Constant.PIN
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject


class CreateNoteFragment : BaseFragment<FragmentCreateNoteBinding,CreateNoteViewModel>() {
    private lateinit var colorAdapter: ColorAdapter
    var color:String ?=BACKGROUND_CARD_COLOR
    var pinValue:Int= PIN
    val args:CreateNoteFragmentArgs by navArgs()
    override fun onFragmentReady() {
        val lockCode=args.lockCode
        snackBar(lockCode)
        setUpRv()
        colorAdapter.setOnItemClickListener {
          color = it
        }
        binding.pin.setOnClickListener {
            pinValue=1
        }
        binding.apply {
            done.setOnClickListener {
                val note = NoteDto(title = title.text.toString(), color = color.toString(), pin = pinValue, noteText = notesDescription.text.toString() )
                mViewModel.createNote(note)
               mViewModel.viewState.onEach {
                   if(it >= 1)
                       snackBar("DoneAdd")
               }.launchIn(lifecycleScope)
            }
            lock.setOnClickListener {
                navigateSafe(CreateNoteFragmentDirections.actionCreateNoteFragmentToLockDialog())
            }
        }
    }
    override val mViewModel: CreateNoteViewModel
        by inject()

    private fun setUpRv() = binding.apply {
        colorAdapter = ColorAdapter(requireContext())
        choiceColorRv.setHasFixedSize(true)
        choiceColorRv.layoutManager =
            LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        choiceColorRv.adapter = colorAdapter
    }
}