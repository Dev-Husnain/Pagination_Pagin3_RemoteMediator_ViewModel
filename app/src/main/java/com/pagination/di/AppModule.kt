package com.pagination.di

import android.content.Context
import androidx.room.Room
import com.pagination.database.QuoteDBClass
import com.pagination.retrofit.QuoteApi
import com.pagination.utils.ConstantObjects.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun getQuoteApi(retrofit: Retrofit): QuoteApi {
        return retrofit.create(QuoteApi::class.java)
    }

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): QuoteDBClass {
        return Room.databaseBuilder(context, QuoteDBClass::class.java, "QuotesDB").build()
    }
}