package com.example.newyorktimes.di

import com.example.newyorktimes.presentation.detailedscreen.ArticleDetailedViewModel
import com.example.newyorktimes.data.OnlineRequestsRepo
import com.example.newyorktimes.data.RequestAPI
import com.example.newyorktimes.domain.repo.ArticleRepo
import com.example.newyorktimes.domain.usecase.ArticleUseCase
import com.example.newyorktimes.domain.usecase.ArticleUseCaseImpl
import com.example.newyorktimes.presentation.listscreen.ArticlesViewModel
import com.example.newyorktimes.presentation.main.SharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val simpleModule = module {
    single<RequestAPI> {
        Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/mostpopular/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RequestAPI::class.java)
    }

    factory<ArticleRepo> { OnlineRequestsRepo(requestAPI = get()) }

    factory<ArticleUseCase> { ArticleUseCaseImpl(get()) }

    viewModel {
        ArticlesViewModel(articleUseCase = get())
    }

    viewModel {
        ArticleDetailedViewModel(savedStateHandle = get())
    }

    viewModel {
        SharedViewModel()
    }


}
