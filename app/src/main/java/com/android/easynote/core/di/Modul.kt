package com.android.easynote.core.di

import android.app.Application
import androidx.room.Room
import com.android.easynote.data.repo.LocalRepo
import com.android.easynote.utils.Constant.DB
import com.android.easynote.data.local.NoteDatabase
import com.android.easynote.domain.*
import com.android.easynote.ui.adapter.ColorAdapter
import com.android.easynote.ui.adapter.NotesAdapter
import com.android.easynote.ui.fragments.operation.OperationsViewModel
import com.android.easynote.ui.fragments.home.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val notesModule = module {

    fun provideDataBase(application: Application): NoteDatabase {
        return Room.databaseBuilder(application, NoteDatabase::class.java, DB)
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideDao(dataBase: NoteDatabase)= dataBase.dao

    single { provideDataBase(androidApplication()) }
    single { provideDao(get()) }
    factory { LocalRepo(get()) }
    factory { ColorAdapter(androidApplication()) }

}
val viewModelModule= module{
    viewModel { OperationsViewModel(get(),get()) }
    viewModel { HomeViewModel(get(),get(),get()) }
}
val adaptersModule= module{
    factory { NotesAdapter() }
}
val useCaseModule= module{
    factory{ GetAllNotesUseCase(get()) }
    factory{ InsertNoteUseCase(get())}
    factory{ DeleteNoteUseCase(get())}
    factory{ EditNoteUseCase(get()) }
    factory{ SearchUseCase(get()) }
}