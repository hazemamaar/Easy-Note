package com.android.easynote.ui.fragments.home

import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.easynote.core.base.BaseFragment
import com.android.easynote.core.extention.log
import com.android.easynote.core.extention.navigateSafe
import com.android.easynote.core.extention.observe
import com.android.easynote.core.extention.toast
import com.android.easynote.data.entities.NoteDto
import com.android.easynote.databinding.FragmentHomeBinding
import com.android.easynote.ui.adapter.NotesAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private val notesAdapter: NotesAdapter by inject()
    override val mViewModel: HomeViewModel by inject()
    var xCode: String? = null
    private lateinit var navController: NavController
    private lateinit var noteT: NoteDto
    private lateinit var notesList: MutableList<NoteDto>
    override fun onFragmentReady() {
        navController = findNavController()
        navigateToCreateNote()
        setUpRv()
        mViewModel.getAllData()
        removeItemTouch()
        editNotes()
        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        mViewModel.apply {
            observe(mViewModel.viewState) {
                handleUiState(it)
            }
        }
    }

    private fun handleUiState(action: HomeAction) {
        when (action) {
            is HomeAction.GetNoteList ->  {
            notesList = action.noteList.toMutableList()
            notesAdapter.noteList = notesList
        }
            is HomeAction.OnRemove -> TODO()
            else -> {}
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun editNotes() {
        notesAdapter.setOnItemClickListener { note ->
            if (note.lock == "" || note.lock == null) {
                navigateSafe(
                    HomeFragmentDirections.actionHomeFragmentToCreateNoteFragment(
                        note
                    )
                )
            } else {
                noteT = note
                xCode = noteT.lock
                note.lock.toString().log("hazzzzz")
                navigateSafe(HomeFragmentDirections.actionHomeFragmentToLockDialog())

            }
        }
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("key")
            ?.observe(viewLifecycleOwner) {
                if (xCode == it) {
                    GlobalScope.launch(Dispatchers.Main) {
                        navigateSafe(
                            HomeFragmentDirections.actionHomeFragmentToCreateNoteFragment(
                                note = noteT
                            )
                        )

                    }
                }

            }
    }


    private fun setUpRv() = binding.apply {
        note_rv.setHasFixedSize(true)
        note_rv.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        note_rv.adapter = notesAdapter
    }

    private fun removeItemTouch() {
        val itemTouchHelper = ItemTouchHelper(removeFromRecycler())
        itemTouchHelper.attachToRecyclerView(binding.noteRv)
    }

    // TODO: handle this method in another class
    private fun removeFromRecycler() = object :
        ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.DOWN or ItemTouchHelper.UP
        ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder,
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
            //Remove swiped item from list and notify the RecyclerView
            if (notesList.isNotEmpty()) {
                val position = viewHolder.adapterPosition
                val item = notesList.removeAt(position)
                notesAdapter.notifyItemRemoved(position)

                val snack = Snackbar.make(requireView(), "Item deleted", Snackbar.LENGTH_LONG)
                snack.setAction("Undo") {
                    notesList.add(position, item)
                    notesAdapter.notifyItemInserted(position)

                }
                snack.addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar, event: Int) {
                        if (event == DISMISS_EVENT_TIMEOUT) {
                            mViewModel.delete(item.id)
                        }
                    }
                })
                snack.show()
            }

        }
    }

    private fun navigateToCreateNote() {
        binding.floatingActionButton.setOnClickListener {
            navigateSafe(HomeFragmentDirections.actionHomeFragmentToCreateNoteFragment(NoteDto()))
        }
    }

}




