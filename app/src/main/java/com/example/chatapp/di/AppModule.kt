package com.example.chatapp.di

import com.example.chatapp.api.ChatApi
import com.example.chatapp.api.ChatApiImpl
import com.example.chatapp.api.ChatService
import com.example.chatapp.repository.ChatRepo
import com.example.chatapp.constant.Constants.BASE_URL
import com.example.personalexpenditure.utils.NetworkInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule  {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(NetworkInterceptor())
        .build()

    @Singleton
    @Provides
    fun getRetrofitInstance(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun getRetrofitServiceInstance(retrofit: Retrofit): ChatService {
        return retrofit.create(ChatService::class.java)
    }

    @Singleton
    @Provides

    fun getChatRepository(chatApiImpl: ChatApiImpl): ChatRepo {
        return ChatRepo((chatApiImpl))
    }


    @Singleton
    @Provides
    fun getChatApi(chatService: ChatService): ChatApi {
        return ChatApiImpl(chatService)
    }


}

