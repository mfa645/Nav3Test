package com.mfa.nav3test.di

import com.mfa.nav3test.note.viewmodel.NoteDetailViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::NoteDetailViewModel)
}