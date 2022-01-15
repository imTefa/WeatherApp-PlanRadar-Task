package com.planradar.data.di

import com.google.gson.Gson
import com.planradar.data.network.Api
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {


    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val API_ID_TAG = "apiId"

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideRetrofit(
        loggingInterceptor: HttpLoggingInterceptor
    ): Retrofit {

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            //.addConverterFactory(GsonConverterFactory.create(Gson()))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .connectionPool(ConnectionPool(5, 5, TimeUnit.SECONDS))
                    .addInterceptor(loggingInterceptor)
                    .build()
            ).build()
    }

    @Provides
    fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)


    //This function simulating getting this apiId from it's stored place
    @Provides
    @Named(API_ID_TAG)
    fun provideApiId(): String = "f5cb0b965ea1564c50c6f1b74534d823"
}