package com.ahr.diaryapp.di

import android.content.Context
import com.ahr.diaryapp.data.repository.MongoRepository
import com.ahr.diaryapp.data.repository.MongoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMongoRepository(
        @ApplicationContext context: Context
    ): MongoRepository {
        return MongoRepositoryImpl(context)
    }
}