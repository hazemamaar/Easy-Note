package com.android.easynote.ui.fragments

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.easynote.R
import com.android.easynote.core.base.BaseFragment
import com.android.easynote.core.extention.log
import com.android.easynote.core.extention.navigateSafe
import com.android.easynote.databinding.FragmentHomeBinding
import com.android.easynote.ui.adapter.ColorAdapter
import com.android.easynote.ui.adapter.NotesAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import java.util.Calendar


class HomeFragment() : BaseFragment<FragmentHomeBinding,HomeViewModel>() {
    lateinit var notesAdapter: NotesAdapter
    override fun onFragmentReady() {
        binding.floatingActionButton.setOnClickListener {
            navigateSafe(HomeFragmentDirections.actionHomeFragmentToCreateNoteFragment(""))
        }
        setUpRv()
        mViewModel.getAllData()
        mViewModel.viewState.onEach {

            if ( it != null && it.isNotEmpty()) {
                val x= it.sortedByDescending { it.pin }
                x.toString().log("onFragmentReady")
                notesAdapter.noteList = x

            }
        }.launchIn(lifecycleScope)
    }

    override val mViewModel: HomeViewModel by  inject()

    private fun setUpRv() = binding.apply {
        notesAdapter = NotesAdapter()
        note_rv.setHasFixedSize(true)
        note_rv.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        note_rv.adapter = notesAdapter
    }

}