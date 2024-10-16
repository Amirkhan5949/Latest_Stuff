package com.example.di_retrofit_livedata.di

import android.content.Context
import androidx.room.Room
import com.example.di_retrofit_livedata.room.QuoteDao
import com.example.di_retrofit_livedata.room.QuoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class QutoeRoomModule {

    @Singleton
    @Provides
    fun getQuoteDataBase(@ApplicationContext context : Context) : QuoteDatabase {
        return Room.databaseBuilder(
            context,
            QuoteDatabase::class.java,
            "my_database"
        ).build()
    }

    @Singleton
    @Provides
    fun getQuoteDao(database: QuoteDatabase) : QuoteDao {
        return database.quoteDao()
    }
}