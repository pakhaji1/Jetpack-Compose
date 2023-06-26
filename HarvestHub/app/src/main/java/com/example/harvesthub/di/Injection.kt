package com.example.harvesthub.di

import com.example.harvesthub.data.RepositoryBarang

object Injection {
    fun provideRepository(): RepositoryBarang {
        return RepositoryBarang.getInstance()
    }
}