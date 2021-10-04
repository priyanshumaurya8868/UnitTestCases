package com.priyanshumaurya8868.unittests.di

import android.content.Context
import androidx.room.Room
import com.priyanshumaurya8868.unittests.data.local.ShoppingDao
import com.priyanshumaurya8868.unittests.data.local.ShoppingItemDatabase
import com.priyanshumaurya8868.unittests.others.Constant.BASE_URL
import com.priyanshumaurya8868.unittests.others.Constant.DATABASE_NAME
import com.priyanshumaurya8868.unittests.data.remote.PixabayAPI
import com.priyanshumaurya8868.unittests.repo.DefaultShoppingRepository
import com.priyanshumaurya8868.unittests.repo.ShoppingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ShoppingItemDatabase::class.java, DATABASE_NAME).build()


    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        dao: ShoppingDao,
        api: PixabayAPI
    ) = DefaultShoppingRepository(dao, api) as ShoppingRepository


    @Singleton
    @Provides
    fun provideShoppingDao(
        database: ShoppingItemDatabase
    ) = database.shoppingDao()

    @Singleton
    @Provides
    fun providePixabayApi(): PixabayAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabayAPI::class.java)
    }

}