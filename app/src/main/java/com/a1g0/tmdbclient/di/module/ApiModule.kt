package com.a1g0.tmdbclient.di.module

import com.a1g0.tmdbclient.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import com.a1g0.tmdbclient.data.api.NetworkService
import com.a1g0.tmdbclient.utils.Constants
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        interceptor.level = when (BuildConfig.BUILD_TYPE) {
            "release" ->{
                HttpLoggingInterceptor.Level.NONE}
            else -> {
                HttpLoggingInterceptor.Level.BODY
            }
        }


        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .callTimeout(60,TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    operator fun invoke(okHttpClient: OkHttpClient) : NetworkService {
        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NetworkService::class.java)
    }


}