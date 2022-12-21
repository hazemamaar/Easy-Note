package com.android.easynote.core.di

import android.app.Application
import android.text.Spannable.Factory
import androidx.room.Room
import com.android.easynote.data.Repo.LocalRepo
import com.android.easynote.utils.Constant.DB
import com.android.easynote.data.local.NoteDao
import com.android.easynote.data.local.NoteDatabase
import com.android.easynote.domain.GetAllNotesUseCase
import com.android.easynote.domain.InsertNoteUseCase
import com.android.easynote.ui.fragments.CreateNoteViewModel
import com.android.easynote.ui.fragments.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
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
    single { LocalRepo(get()) }
    single{ GetAllNotesUseCase(get())}
    single{ InsertNoteUseCase(get())}
    viewModel { CreateNoteViewModel(get()) }
    viewModel { HomeViewModel(get()) }

}
